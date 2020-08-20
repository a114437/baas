var views = [
	{
		"id" : "test",
		"view" : TestView
	},
	{
		"id" : "foo",
		"view" : fooView
	},
	{
		"id" : "bar",
		"view" : barView
	},
	{
		"id" : "car",
		"view" : carView
	},
	{
		"id" : "car1",
		"view" : carView1
	},
	{
		"id" : "test-table",
		"view" : tableView
	}
];


const routes = [];

addRouter(leftMenus);

function addRouter(menus) {
	for (var i in menus) {
		var templateId = menus[i].entity.template;
		var url = menus[i].entity.url;
		var id = menus[i].entity.id;
		var childs = menus[i].entity.childs;
		if (childs != null) {
			addRouter(menus[i].entity.childs);
		} else {
			for (var j in views) {
				if (views[j].id == templateId) {
					routes.push({
						path : "/" + url,
						component : views[j].view
					});
				}
			}
		}
	}
}


const router = new VueRouter({
	/* mode:"history", */
	routes // (缩写) 相当于 routes: routes
})