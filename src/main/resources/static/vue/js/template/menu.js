Vue.component('nav-menu', {
	props : [ 'menus',"tabs","edit-tab"],
	data(){
		return {
			currentTabs:[],
			currentEditTab:''
		}
	},
	template : `<div class="navMenu">
				    <label v-for="navMenu in menus">
					  <el-menu-item v-if="navMenu.entity.childs==null&&navMenu.entity"
				                    :key="navMenu.entity.id" :data="navMenu" :index="'/'+navMenu.entity.url"
				                    @click="addContent(navMenu.entity,this)">
				        <i :class="navMenu.entity.icon"></i>
				        <span slot="title" class="menu_title">{{navMenu.entity.name}}</span>
				      </el-menu-item>
				      <el-submenu v-if="navMenu.entity.childs&&navMenu.entity"
				                  :key="navMenu.entity.id" :data="navMenu" :index="navMenu.entity.id">
				        <template slot="title">
				          <i :class="navMenu.entity.icon"></i>
				          <span slot="title" class="menu_title"> {{navMenu.entity.name}}</span>
				        </template>
				        <nav-menu :menus="navMenu.entity.childs"></nav-menu>
				      </el-submenu>      
				    </label>
				  </div>`,
	 methods:{
		 addContent(entity,data){
			this.currentTabs = this.$store.state.editableTabs;
			this.currentEditTab = this.$store.state.editableTabsValue;
			var index = -1;
			for(var i in this.currentTabs){
				this.currentTabs[i].active = false;
				if(this.currentTabs[i].name==entity.id){
					index = i;
				}
			}
			if(index==-1){
				this.currentTabs.push({
					title: entity.name,
					name: entity.id,
					url:entity.url
				});
			}else{
				this.currentTabs.splice(index,1);
				this.currentTabs.splice(index,0,{
					title: entity.name,
					name: entity.id,
					url:entity.url
				});
			}
			this.$store.commit("updateTabVal",entity.id);
		    this.$store.commit("updateTabs",this.currentTabs);
			this.$router.push({ path: entity.url});
		 },
		 checkDefault(data,url){
			 for(var i in data){
				 var navMenu = data[i];
				 if(navMenu.entity.childs==null&&navMenu.entity&&'/'+navMenu.entity.url==url){
					 this.addContent(navMenu.entity);//添加tab
				 }else{
					 this.checkDefault(navMenu.entity.childs,url);
				 }
			 }
		 }
	 },
	 watch:{
	 },
	 computed:{
	 },
	 mounted(){
		 this.checkDefault(this.menus,this.$route.path);
	 }
})