const store = new Vuex.Store({
	state : {
		editableTabsValue : '',
		editableTabs : []
	},
	mutations : {
		updateTabs (state, tabs) {
			state.editableTabs = tabs;
		},
		updateTabVal (state, tabVal) {
			state.editableTabsValue = tabVal;
		}
	}
})


new Vue({
	el : '#app',
	router ,
	store ,
	data : {
		isCollapse : false,
		showLogo : true,
		toggleMunuStyle : {
			"padding-left" : "39px"
		},
		asideStyle : {
			"width" : "300px"
		},
		leftMenus : leftMenus
	},
	beforeCreate () { // 这是我们遇到的第一个生命周期函数，表示实例完全被创建出来之前，会执行它
		console.log("beforeCreate");
	// 注意： 在 beforeCreate 生命周期函数执行的时候，data 和 methods 中的 数据都还没有没初始化
	},
	created () { // 这是遇到的第二个生命周期函数
		/* axios.get('./data/menus.json').then(function(response){
			this.leftMenus = response.data;
		}); */
		//  在 created 中，data 和 methods 都已经被初始化好了！
		// 如果要调用 methods 中的方法，或者操作 data 中的数据，最早，只能在 created 中操作
	},
	beforeMount () { // 这是遇到的第3个生命周期函数，表示 模板已经在内存中编辑完成了，但是尚未把 模板渲染到 页面中
		// 在 beforeMount 执行的时候，页面中的元素，还没有被真正替换过来，只是之前写的一些模板字符串
	},
	mounted () { // 这是遇到的第4个生命周期函数，表示，内存中的模板，已经真实的挂载到了页面中，用户已经可以看到渲染好的页面了
		// 注意： mounted 是 实例创建期间的最后一个生命周期函数，当执行完 mounted 就表示，实例已经被完全创建好了，此时，如果没有其它操作的话，这个实例，就静静的 躺在我们的内存中，一动不动
	},
	// 接下来的是运行中的两个事件
	beforeUpdate () { // 这时候，表示 我们的界面还没有被更新【数据被更新了吗？  数据肯定被更新了】
		// 得出结论： 当执行 beforeUpdate 的时候，页面中的显示的数据，还是旧的，此时 data 数据是最新的，页面尚未和 最新的数据保持同步
	},
	updated () {
		// updated 事件执行的时候，页面和 data 数据已经保持同步了，都是最新的
		//this.existTabs = this.editableTabs;
	},
	methods : {
		toggleMenu () {
			if (this.isCollapse) {
				this.isCollapse = false;
				this.showLogo = true;
				this.toggleMunuStyle["padding-left"] = '39px';
				this.asideStyle["width"] = '300px';
			} else {
				this.isCollapse = true;
				this.showLogo = false;
				this.toggleMunuStyle["padding-left"] = '20px';
				this.asideStyle["width"] = 'auto';
			}
		},
		toggleTab (tab, event) {
			this.$store.commit("updateTabVal", this.editableTabs[tab._data.index].name);
			this.$route.meta.ok = false;
			this.$router.push({
				path : this.editableTabs[tab._data.index].url
			});
		},
		removeTab (targetName) {
			let tabs = this.$store.state.editableTabs;
			let activeName = this.$store.state.editableTabsValue;
			let url = '/';
			if (activeName === targetName) {
				tabs.forEach((tab, index) => {
					if (tab.name === targetName) {
						let nextTab = tabs[index + 1] || tabs[index - 1];
						if (nextTab) {
							activeName = nextTab.name;
							url = nextTab.url;
						}
					}
				});
				this.$store.commit("updateTabVal", activeName);
				this.$route.meta.ok = true;
				this.$router.push({
					path : url
				});
			}

			this.$store.commit("updateTabs", tabs.filter(tab => tab.name !== targetName));
			if (this.editableTabs.length == 0) {
				this.$store.commit("updateTabVal", "");
			}
		}
	},
	computed : {
		// 计算属性的 getter
		editableTabs : function() {
			// `this` 指向 vm 实例
			return this.$store.state.editableTabs;
		},
		editableTabsValue : function() {
			// `this` 指向 vm 实例
			return this.$store.state.editableTabsValue;
		}
	},
	watch : {
		$route (to, from) {
			//router.push({ path: to.path})
		}
	}
})