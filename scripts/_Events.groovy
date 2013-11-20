eventCreateWarStart = { warName, myDir ->
	println 'BORRANDO cglib-2.2.jar!'	
	ant.delete(file:"${myDir}/WEB-INF/lib/cglib-2.2.jar")	
 }
