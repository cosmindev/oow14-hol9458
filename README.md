OOW HOL 9458
============i
In order to run this application on Oracle Public Cloud, you must follow these steps. 

# 1. Create DB and JCS PaaS instances on Oracle Public Cloud
When prompted to provide a VM Public Key (to do SSH), use the existing one at 'etc/ssh/hol9458_id_rsa.pub'.

 * Create a Database Cloud Service (non-VI) instance - 12c
 * Create a Java Cloud Service (VI) instance - 12c

## Creating a DBCS instance
The Database Cloud Service instance you must create must have the following configuration:

 * Service Level: Oracle Database Cloud Service (important: not Virtual Image)
 * Software Release: Oracle Database 12c Release 1
 * Software Edition: Enterprise Edition

In the Service Details form, provide a name for your DBCS instance. For your conveniency, please use 'OOW14HOL9458'. See list below for other configurations:

 * Instance Name: OOW14HOL9458
 * Compute Shape: OC3 - 1 OCPU
 * VM Public Key: <upload etc/ssh/hol9458_id_rsa.pub>
 * Administration Password: Welcome1#
 * Confirm Password: Welcome1#

Since this is an instance for demonstration, do not select a Backup Destination. 

 * Backup Destination: None

Leave all other fields unchanged with their default values. Finish the process to start provisioning this instance.

Do not proceed to next step while still provisioning. Wait for it to finish.

## Create a JCS instance
The Java Cloud Service instance you must create must have the following configuration:

 * Service Level: Oracle Java Cloud Service - Virtual Image
 * Software Release: Oracle WebLogic Server 12c (12.1.2.0)
 * Software Edition: Enterprise Edition (important: not with Coherence)

In the Instance Details form, provide a name for your JCS instance. For your conveniency, please use 'OOW14HOL9458'. See list below for other configurations:

 * Instance Name: OOW14HOL9458
 * Compute Shape: OC3 - 1 OCPU
 * VM Public Key: <upload etc/ssh/hol9458_id_rsa.pub>
 * User Name: weblogic
 * Password: Welcome1#
 * Confirm Password: Welcome1#
 * Database Configuration: select previously created DBCS, say 'OOW14HOL9458'
 * Database Administrator User Name: system
 * Password: Welcome1#
 * Load Balancer: No

Leave all other fields unchanged with their default values. Finish the process to start provisioning this instance.

You can advance to Step 2 below while JCS is provisioning.

# 2. Activate the OE Schema in your newly created Oracle DBCS instance
After your DBCS has been provisioned, go to the instance details and find what is its Public IP address. 
Connect with SSH using the private key 'hol9458_id_rsa' as follows:
'''
$ ssh oracle@<dbcs-public-ip-address> -i etc/ssh/hol9458_id_rsa
'''

Call the following SQL\*Plus command to activate the OE Schema in Oracle DB 12c:

'''
$ sqlplus / as sysdba
alter session set container=pdb1;
alter user oe account unlock identified by welcome1;
$ sqlplus oe/welcome1@pdb1
'''
