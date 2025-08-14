dataSource {
    pooled = true
    //driverClassName = "org.postgresql.Driver"
    //dialect = "org.hibernate.dialect.PostgreSQLDialect"
    username = "sa"
    password = ""
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
			driverClassName = "org.h2.Driver"
			dialect = "org.hibernate.dialect.H2Dialect"
			url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
			logSql = true
		}
	}
	
	test {
		dataSource {
			//dbCreate = "update"
			url = "jdbc:postgresql://dbinstance.bullbix.com:5432/hojaenfermeria"
			logSql = true
		}
	}
	
	production {
		dataSource {
			url = "jdbc:postgresql://dbinstance.bullbix.com:5432/hojaenfermeria"
		}
	}
}
