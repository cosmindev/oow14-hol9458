package com.oracle.cloud.demo.oe.web;

import com.oracle.cloud.demo.oe.web.util.BasketItem;
import com.oracle.cloud.demo.oe.sessions.ProductInformationFacade;
import com.oracle.cloud.demo.oe.sessions.ProductInformationFacadeRemote;
import com.oracle.cloud.demo.oe.entities.ProductInformation;
import com.oracle.cloud.demo.oe.web.util.JsfUtil;
import com.oracle.cloud.demo.oe.web.util.PaginationHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

@ViewScoped
@ManagedBean(name = "productInformationController")
public class ProductInformationController implements Serializable {
	private ProductInformation current;
	private DataModel items = null;
	@EJB
	private ProductInformationFacadeRemote ejbFacade;
	private PaginationHelper pagination;
	private int selectedItemIndex;
	@Inject
	private BasketController basket;

	public ProductInformationController() {
	}

	public ProductInformation getSelected() {
		if (current == null) {
			current = new ProductInformation();
			selectedItemIndex = -1;
		}
		return current;
	}

	private ProductInformationFacade getFacade() {
		return (ProductInformationFacade) ejbFacade;
	}

	public PaginationHelper getPagination() {
		if (pagination == null) {
			pagination = new PaginationHelper(10) {
				@Override
				public int getItemsCount() {
					return getFacade().count();
				}

				@Override
				public DataModel createPageDataModel() {
					return new ListDataModel(getFacade().findRange(
							new int[] { getPageFirstItem(),
									getPageFirstItem() + getPageSize() }));
				}
			};
		}
		return pagination;
	}

	public String prepareList() {
		recreateModel();
		return "List";
	}

	public String prepareView() {
		current = (ProductInformation) getItems().getRowData();
		selectedItemIndex = pagination.getPageFirstItem()
				+ getItems().getRowIndex();
		return "View";
	}

	public String prepareCreate() {
		current = new ProductInformation();
		selectedItemIndex = -1;
		return "Create";
	}

	public String create() {
		try {
			getFacade().create(current);
			JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle")
					.getString("ProductInformationCreated"));
			return prepareCreate();
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle")
					.getString("PersistenceErrorOccured"));
			return null;
		}
	}

	public String prepareEdit() {
		current = (ProductInformation) getItems().getRowData();
		selectedItemIndex = pagination.getPageFirstItem()
				+ getItems().getRowIndex();
		return "Edit";
	}

	public String update() {
		try {
			getFacade().edit(current);
			JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle")
					.getString("ProductInformationUpdated"));
			return "View";
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle")
					.getString("PersistenceErrorOccured"));
			return null;
		}
	}

	public String destroy() {
		current = (ProductInformation) getItems().getRowData();
		selectedItemIndex = pagination.getPageFirstItem()
				+ getItems().getRowIndex();
		performDestroy();
		recreatePagination();
		recreateModel();
		return "List";
	}

	public String destroyAndView() {
		performDestroy();
		recreateModel();
		updateCurrentItem();
		if (selectedItemIndex >= 0) {
			return "View";
		} else {
			// all items were removed - go back to list
			recreateModel();
			return "List";
		}
	}

	private void performDestroy() {
		try {
			getFacade().remove(current);
			JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle")
					.getString("ProductInformationDeleted"));
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle")
					.getString("PersistenceErrorOccured"));
		}
	}

	private void updateCurrentItem() {
		int count = getFacade().count();
		if (selectedItemIndex >= count) {
			// selected index cannot be bigger than number of items:
			selectedItemIndex = count - 1;
			// go to previous page if last page disappeared:
			if (pagination.getPageFirstItem() >= count) {
				pagination.previousPage();
			}
		}
		if (selectedItemIndex >= 0) {
			current = getFacade().findRange(
					new int[] { selectedItemIndex, selectedItemIndex + 1 })
					.get(0);
		}
	}

	public DataModel getItems() {
		if (items == null) {
			items = getPagination().createPageDataModel();
		}
		return items;
	}

	private void recreateModel() {
		items = null;
	}

	private void recreatePagination() {
		pagination = null;
	}

	public String next() {
		getPagination().nextPage();
		recreateModel();
		return "List";
	}

	public String previous() {
		getPagination().previousPage();
		recreateModel();
		return "List";
	}

	public SelectItem[] getItemsAvailableSelectMany() {
		return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
	}

	public SelectItem[] getItemsAvailableSelectOne() {
		return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
	}

	public ProductInformation getProductInformation(java.lang.Integer id) {
		return ejbFacade.find(id);
	}

	@FacesConverter(forClass = ProductInformation.class)
	public static class ProductInformationControllerConverter implements
			Converter {
		@Override
		public Object getAsObject(FacesContext facesContext,
				UIComponent component, String value) {
			if (value == null || value.length() == 0) {
				return null;
			}
			ProductInformationController controller = (ProductInformationController) facesContext
					.getApplication()
					.getELResolver()
					.getValue(facesContext.getELContext(), null,
							"productInformationController");
			return controller.getProductInformation(getKey(value));
		}

		Integer getKey(String value) {
			java.lang.Integer key;
			key = Integer.valueOf(value);
			return key;
		}

		String getStringKey(java.lang.Integer value) {
			StringBuilder sb = new StringBuilder();
			sb.append(value);
			return sb.toString();
		}

		@Override
		public String getAsString(FacesContext facesContext,
				UIComponent component, Object object) {
			if (object == null) {
				return null;
			}
			if (object instanceof ProductInformation) {
				ProductInformation o = (ProductInformation) object;
				return getStringKey(o.getProductId());
			} else {
				throw new IllegalArgumentException("object " + object
						+ " is of type " + object.getClass().getName()
						+ "; expected type: "
						+ ProductInformation.class.getName());
			}
		}
	}

	@PostConstruct
	public void initialize() {
		tabelaProdutos.clear();
		produtosSelecionados.clear();
		produtosMap.clear();
		produtosDisponiveis.clear();
		for (ProductInformation prodInfo : ejbFacade.findAll()) {
			produtosMap.put(prodInfo.getProductId(), prodInfo);
			produtosDisponiveis.add(prodInfo);
		}
	}

	private List<BasketItem> tabelaProdutos = new ArrayList<>();
	private List<String> produtosSelecionados = new ArrayList<>();
	private final Map<Integer, ProductInformation> produtosMap = new HashMap<>();
	private Set<ProductInformation> produtosDisponiveis = new HashSet<>();

	public void removerItemTabela(BasketItem item) {
		tabelaProdutos.remove(item);
		produtosDisponiveis.add(item.getProduct());
	}

	public void removeAllItems() {
		tabelaProdutos.clear();
		initialize();
	}

	public void incluirProduto() {
		for (String i : produtosSelecionados) {
			ProductInformation pi = produtosMap.get(Integer.valueOf(i));
			produtosDisponiveis.remove(pi);
			BasketItem prodQtdVO = new BasketItem();
			prodQtdVO.setProduct(pi);
			prodQtdVO.setQuantity(1);
			tabelaProdutos.add(prodQtdVO);
		}
	}

	public List<BasketItem> getTabelaProdutos() {
		return tabelaProdutos;
	}

	public void setTabelaProdutos(List<BasketItem> tabelaProdutos) {
		this.tabelaProdutos = tabelaProdutos;
	}

	public List<String> getProdutosSelecionados() {
		return produtosSelecionados;
	}

	public void setProdutosSelecionados(List<String> produtosSelecionados) {
		this.produtosSelecionados = produtosSelecionados;
	}

	public Set<ProductInformation> getProdutosDisponiveis() {
		return produtosDisponiveis;
	}

	public void setProdutosDisponiveis(
			Set<ProductInformation> produtosDisponiveis) {
		this.produtosDisponiveis = produtosDisponiveis;
	}

	public String addToBasket() {
		List<BasketItem> selecionados = new ArrayList<>();
		for (BasketItem p : tabelaProdutos) {
			if (p.getQuantity() > 0) {
				selecionados.add(p);
			}
		}
		basket.addMoreItems(selecionados);
		return "/shop/Basket";
	}
}