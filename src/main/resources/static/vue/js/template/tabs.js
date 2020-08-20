Vue.component('tabs', {
	props : [ 'menus' ],
	template : `
					<el-tabs type="card" closable>
						<template v-for="navMenu in menus">
						   <el-tab-pane v-if="navMenu.entity.childs==null&&navMenu.entity&&navMenu.entity.url=='/test'"
							    :key="navMenu.entity.id"
							    :label="navMenu.entity.name"
							    :name="navMenu.entity.url"
							    :sss="navMenu.entity.url"
							  >
						  </el-tab-pane>
						  <tabs v-else :menus="navMenu.entity.childs"></tabs>
						</template>
					</el-tabs>
				`,
	mounted(){
	}
})
