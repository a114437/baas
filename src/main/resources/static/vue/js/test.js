Vue.component('test', { 
		   props: ['name'],
		   data(){
		   		return {
		   			cdata:this.name
		   		}
		   },
		   template: `<div @click="test()">{{cdata}}</div>` ,
		   methods:{
				test(){
					this.cdata = Math.ceil(Math.random()*10);
				}
		   },
		   watch:{
				cdata(newData,oldData){
					alert("源数据:"+oldData+",新数据:"+newData);
				}		   
		   }
});

