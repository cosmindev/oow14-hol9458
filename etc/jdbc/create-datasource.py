# variables
username = 'weblogic'
password = 'welcome1'
URL='t3://localhost:12120'
datasource_name='jdbc/OE'
datasource_jndi='jdbc/OE'
#datasource_url='jdbc:oracle:thin:@//oow14hol9458-db:1521/PDB1.compute-staug305744476.oraclecloud.internal'
datasource_url='jdbc:oracle:thin:@//192.168.56.101:1521/pdborcl'
datasource_username='oe'
datasource_password='oe'
datasource_target='Server-0'
datasource_targetType='Server'
datasource_targetAdmin='AdminServer'

# Connect and Activate Edit Mode
connect(username,password,URL)
edit()
startEdit()

# Create DataSource jdbc/OE
cd('/')
cmo.createJDBCSystemResource('OE')

cd('/JDBCSystemResources/OE/JDBCResource/OE')
cmo.setName(datasource_name)

cd('/JDBCSystemResources/OE/JDBCResource/OE/JDBCDataSourceParams/OE')
set('JNDINames',jarray.array([String(datasource_jndi)], String))

cd('/JDBCSystemResources/OE/JDBCResource/OE/JDBCDriverParams/OE')
cmo.setUrl('jdbc:oracle:thin:@192.168.1.102:1521:xe')
cmo.setDriverName('oracle.jdbc.xa.client.OracleXADataSource')
setEncrypted('Password', 'Password_1410283437701', 'F:/Windows/DevTools/oracle_home/wls1213/user_projects/domains/test_domain/Script1410283384959Config','F:/Windows/DevTools/oracle_home/wls1213/user_projects/domains/test_domain/Script1410283384959Secret')

cd('/JDBCSystemResources/OE/JDBCResource/OE/JDBCConnectionPoolParams/OE')
cmo.setTestTableName('SQL ISVALID\r\n\r\n')

cd('/JDBCSystemResources/OE/JDBCResource/OE/JDBCDriverParams/OE/Properties/OE')
cmo.createProperty('user')
cd('/JDBCSystemResources/OE/JDBCResource/OE/JDBCDriverParams/OE/Properties/OE/Properties/user')
cmo.setValue('oe')

cd('/JDBCSystemResources/OE/JDBCResource/OE/JDBCDataSourceParams/OE')
cmo.setGlobalTransactionsProtocol('TwoPhaseCommit')

cd('/JDBCSystemResources/OE')
set('Targets',jarray.array([ObjectName('com.bea:Name='+datasource_targetAdmin+',Type=Server'),ObjectName('com.bea:Name='+datasource_target+',Type='+datasource_targetType)], ObjectName))

activate()

