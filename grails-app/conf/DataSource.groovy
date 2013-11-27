dataSource {
	url = "jdbc:informix-sqli://192.168.10.10:1526/saihweb:informixserver=ol_inrserver" //desarrollo
	//url = "jdbc:informix-sqli://192.168.10.12:1526/saihweb:informixserver=ol_inrserver" //produccion
	//url = "jdbc:informix-sqli://192.168.10.12:1527/saihweb:informixserver=ol_adminserver" //preproduccion
	driverClassName = "com.informix.jdbc.IfxDriver"
	username = "informix"
	password = "informix"
	pooled = true
	dialect = "org.hibernate.dialect.InformixDialect"
}

hibernate {
	cache.use_second_level_cache = true
	cache.use_query_cache = true
	cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
	validator.apply_to_ddl = false
	validator.autoregister_listeners = false
	format_sql = true
}
// environment specific settings
environments {
	development {
		dataSource {
		   logSql = true
		}
	}
	
	test {
		dataSource {
		   logSql = true
		}
	}
	
	production {
		dataSource {
			jndiName = "java:comp/env/jdbc/INR/Informix"
			/*url = "jdbc:informix-sqli://192.168.10.12:1526/saihweb:informixserver=ol_inrserver"
			properties {
			   maxActive = -1
			   minEvictableIdleTimeMillis=1800000
			   timeBetweenEvictionRunsMillis=1800000
			   numTestsPerEvictionRun=3
			   testOnBorrow=true
			   testWhileIdle=true
			   testOnReturn=true
			   validationQuery="select first 1 * from systables"
			}*/
		}		
	}
}
