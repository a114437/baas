var template =
`<div>	
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters" style="margin-top:22px;margin-left:22px;">
				<el-form-item>
					<span>姓名</span>
				</el-form-item>
				<el-form-item>
					<el-input v-model="filters.name" placeholder="姓名"></el-input>
				</el-form-item>
				<el-form-item>
					<span>选择</span>
				</el-form-item>
				<el-form-item>
					<el-select v-model="options.defaultVal" filterable placeholder="请选择">
						<el-option
							v-for="item in options.list"
							:key="item.value"
							:label="item.label"
							:value="item.value">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<span>日期</span>
				</el-form-item>
				<el-form-item>
					<el-date-picker
						v-model="date1"
						type="date"
						placeholder="选择日期">
					</el-date-picker>
				</el-form-item>
				<el-form-item>
					<span>年份</span>
				</el-form-item>
				<el-form-item>
					<el-date-picker
						v-model="year"
						type="year"
						placeholder="选择年">
					</el-date-picker>
				</el-form-item>
				<br>
				<el-form-item>
					<span>月份</span>
				</el-form-item>
				<el-form-item>
					<el-date-picker
				      v-model="month"
				      type="month"
				      placeholder="选择月">
				    </el-date-picker>
				</el-form-item>
				<el-form-item>
					<span>时间</span>
				</el-form-item>
				<el-form-item>
					<el-time-select
					  v-model="time"
					  :picker-options="{
					    start: '00:00',
					    step: '00:15',
					    end: '23:45'
					  }"
					  placeholder="选择时间">
					</el-time-select>
				</el-form-item>
				<el-form-item>
					<span>日期区间</span>
				</el-form-item>
				<el-form-item>
					<el-date-picker
				      v-model="date2"
				      type="daterange"
				      range-separator="至"
				      start-placeholder="开始日期"
				      end-placeholder="结束日期">
				    </el-date-picker>
				</el-form-item>
				<br>
				<el-form-item>
					<span>月份区间</span>
				</el-form-item>
				<el-form-item>
					<el-date-picker
				      v-model="month2"
				      type="monthrange"
				      range-separator="至"
				      start-placeholder="开始月份"
				      end-placeholder="结束月份">
				    </el-date-picker>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getUsers">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleAdd">新增</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table stripe :data="users" highlight-current-row v-loading="listLoading"
					:cell-style="cellStyle"	@selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection" width="55">
			</el-table-column>
			<el-table-column type="index" width="60">
			</el-table-column>
			<el-table-column prop="name" label="姓名" width="120" sortable>
			</el-table-column>
			<el-table-column prop="sex" label="性别" width="100" :formatter="formatSex" sortable>
			</el-table-column>
			<el-table-column prop="age" label="年龄" width="100" sortable>
			</el-table-column>
			<el-table-column prop="birth" label="生日" width="120" sortable>
			</el-table-column>
			<el-table-column prop="addr" label="地址" min-width="180" sortable>
			</el-table-column>
			<el-table-column label="操作" width="150">
				<template slot-scope="scope">
					<el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
					<el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar" style="text-align: center;">
			<el-pagination layout="total, sizes, prev, pager, next, jumper"
				 @size-change="handleSizeChange" 
				 @current-change="handleCurrentChange" 
				 :page-size="rows" 
				 :page-sizes="rowsList" 
				 :total="total">
			</el-pagination>
		</el-col>
		
		<!--新增界面-->
		<el-dialog title="新增"  :visible.sync="addFormVisible" :close-on-click-modal="false">
			<el-form  label-width="80px" >
				<el-form-item label="姓名" prop="name">
					<el-input auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="性别">
					<el-radio-group>
						<el-radio class="radio" :label="1">男</el-radio>
						<el-radio class="radio" :label="0">女</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="年龄">
					<el-input-number :min="0" :max="200"></el-input-number>
				</el-form-item>
				<el-form-item label="生日">
					<el-date-picker type="date" placeholder="选择日期" ></el-date-picker>
				</el-form-item>
				<el-form-item label="地址">
					<el-input type="textarea"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="addFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
			</div>
		</el-dialog>
	</section>
</div>`	
	
Vue.component('t-table', {
	template:template,
	data() {
		return {
			filters: {
				name: ''
			},
			users: [],
			total: 100,
			page: 1,
			rows:10,
			rowsList:[
				5,10,20,30,40,50,100
			],
			listLoading: false,
			options:{
				defaultVal:'1',
				list:[
						{
				          value: '1',
				          label: 'A'
				        }, {
				          value: '2',
				          label: 'B'
				        }, {
				          value: '3',
				          label: 'C'
				        }
					]
			},
			date1:'',
			year:'',
			month:'',
			time:'',
			date2:'',
			month2:'',
			addFormVisible:false,
			addLoading:false,
			sels: []//列表选中列
		}
	},
	methods: {
		//性别显示转换
		formatSex: function (row, column) {
			return row.sex == 1 ? `男`: row.sex == 0 ? `女` : '未知';
		},
		cellStyle(row,column,rowIndex,columnIndex){//根据报警级别显示颜色
			if(row.column.label=='性别'){
				if(row.row.sex==1){
					return 'color:green'
				}else if(row.row.sex==0 ){
					return 'color:red'
				}else{
					return '';
				}
			}
	    },
	    handleSizeChange(val){
	    	this.rows = val;
	    	console.log("条数："+this.rows);
	    },
		handleCurrentChange(val) {
			this.page = val;
		},
		//获取用户列表
		getUsers() {
			let para = {
				page: this.page,
				name: this.filters.name
			};
			
			this.users = [
						{
							id:"1",
							name:"张三1",
							sex:"1",
							age:"1",
							birth:"2019-01-01",
							addr:"地址1"
						},
						{
							id:"2",	
							name:"张三2",
							sex:"1",
							age:"1",
							birth:"2019-01-01",
							addr:"地址1"
						},
						{
							id:"3",
							name:"张三3",
							sex:"0",
							age:"1",
							birth:"2019-01-01",
							addr:"地址1"
						},
						{
							id:"4",
							name:"张三4",
							sex:"1",
							age:"1",
							birth:"2019-01-01",
							addr:"地址1"
						},
						{
							id:"5",
							name:"张三5",
							sex:"0",
							age:"1",
							birth:"2019-01-01",
							addr:"地址1"
						},
						{
							id:"6",	
							name:"张三6",
							sex:"1",
							age:"1",
							birth:"2019-01-01",
							addr:"地址1"
						},
						{
							id:"7",
							name:"张三7",
							sex:"1",
							age:"1",
							birth:"2019-01-01",
							addr:"地址1"
						},
						{
							id:"8",
							name:"张三8",
							sex:"0",
							age:"1",
							birth:"2019-01-01",
							addr:"地址1"
						},
						{
							id:"9",
							name:"张三9",
							sex:"1",
							age:"1",
							birth:"2019-01-01",
							addr:"地址1"
						},
						{
							id:"10",
							name:"张三10",
							sex:"1",
							age:"1",
							birth:"2019-01-01",
							addr:"地址1"
						}
					
					]
		},
		//删除
		handleDel: function (index, row) {
			this.$confirm('确认删除该记录吗?', '提示', {
				type: 'warning'
			}).then(() => {
				this.$message({
					message: '删除成功',
					type: 'success'
				});
			}).catch(() => {

			});
		},
		//显示编辑界面
		handleEdit: function (index, row) {
			
		},
		//显示新增界面
		handleAdd: function () {
			this.addFormVisible = true;
		},
		//编辑
		editSubmit: function () {
			
		},
		//新增
		addSubmit: function () {
			this.addLoading = true;
			var temp = this;
			setTimeout(function(){ 
				temp.addLoading = false;
			}, 2000);
		},
		selsChange: function (sels) {
			this.sels = sels;
		},
		//批量删除
		batchRemove: function () {
			var ids = this.sels.map(item => item.id).toString();
			console.log(ids);
			this.$confirm('确认删除选中记录吗？', '提示', {
				type: 'warning'
			}).then(() => {
				this.$message({
					message: '删除成功',
					type: 'success'
				});
			}).catch(() => {

			});
		}
	},
	mounted() {
		this.getUsers();
	}
})	
	
	