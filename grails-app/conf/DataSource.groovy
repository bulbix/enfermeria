dataSource {
	pooled = true
    driverClassName = "org.postgresql.Driver"
    dialect = "org.hibernate.dialect.PostgreSQLDialect"
    username = "bulbix"
    password = "garbage1"
}

hibernate {
	cache.use_second_level_cache = true
	//cache.use_query_cache = true
	cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
	validator.apply_to_ddl = false
	validator.autoregister_listeners = false
	format_sql = false
}
// environment specific settings
environments {
	development {
		dataSource {
			dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
			url = "jdbc:postgresql://bulbix.cbhbdysmftcx.us-west-2.rds.amazonaws.com:5432/hojaenfermeria"
			logSql = true
		}
	}
	
	test {
		dataSource {
			dbCreate = "update"
			url = "jdbc:postgresql://bulbix.cbhbdysmftcx.us-west-2.rds.amazonaws.com:5432/hojaenfermeria"
			logSql = true
		}
	}
	
	production {
		dataSource {
			url = "jdbc:postgresql://bulbix.cbhbdysmftcx.us-west-2.rds.amazonaws.com:5432/hojaenfermeria"
		}
	}
}
