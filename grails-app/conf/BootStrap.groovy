class BootStrap {

    def init = { servletContext ->
		
		Date.metaClass.getAge = { new Date().year - delegate.year }
		
    }
    def destroy = {
    }
}
