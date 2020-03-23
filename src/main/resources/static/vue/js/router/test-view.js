var TestView = {
	template : "#test"
}

var fooView = {
	template : "#foo"
}

var barView = {
	template : "#bar"
}

var tableView = {
	template:"#test-table"
}

var carView = {
	data () {
		return {
			cdata : "Car"
		}
	},
	template : '#car',
	methods : {
		test () {
			this.cdata = Math.ceil(Math.random() * 10);
		}
	},
	watch : {
		cdata (newData, oldData) {
			alert("源数据:" + oldData + ",新数据:" + newData);
		}
	}
}
var carView1 = {
	data () {
		return {
			cdata : "Car1"
		}
	},
	template : '#car1',
	methods : {
		test () {
			this.cdata = Math.ceil(Math.random() * 10);
		}
	},
	watch : {
		cdata (newData, oldData) {
			alert("源数据:" + oldData + ",新数据:" + newData);
		}
	}
}


