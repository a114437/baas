<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path_home = request.getContextPath();
String basepath_home = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path_home+"/";
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    	 <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
		<title>快速开发平台</title>
		
		<!-- zTree引入 -->
		<link rel="stylesheet" href="<%=path_home %>/plugin/zTree_v3-master/css/demo.css" type="text/css">
		<link rel="stylesheet" href="<%=path_home %>/plugin/zTree_v3-master/css/sidebarStyle/zTreeStyle.css" type="text/css">		
		
		<!-- 引入样式 -->
		<link href="<%=path_home%>/plugin/bootstrap/css/bootstrap.css" rel="stylesheet">
		<link href="<%=path_home %>/css/all.css" rel="stylesheet">
		<link rel="stylesheet" href="<%=path_home %>/css/topStyle1.css" /> 
		<!--皮肤 -->
	 	 <link rel="stylesheet" href="<%=path_home %>/css/skin/skin1.css" />   <!-- 默认 -->
		<%--<link rel="stylesheet" href="<%=path_home %>/css/skin/skin2.css" />--%>  <!-- 蓝白 -->
		<%-- <link rel="stylesheet" href="<%=path_home %>/css/skin/skin3.css" /> --%><!-- 紫色 --> 
		 <!--图标-->
		<link rel="stylesheet" href="<%=path_home %>/plugin/fontawesome-4.2.0_ie7/4.2.0/css/font-awesome.min.css" />
		<link rel="stylesheet" href="<%=path_home %>/plugin/fontawesome-4.2.0_ie7/4.2.0/css/font-awesome-ie7.min.css" />
		<link rel="stylesheet" href="<%=path_home %>/plugin/linearicons/style.css" />
		 
		 <!-- bootStrapTable -->
		 <link rel="stylesheet" href="  <%=path_home %>/plugin/bootstrap-table-develop/src/bootstrap-table.css" >
		
		<!-- 表单验证 -->
		<link rel="stylesheet" href="<%=path_home %>/plugin/bootstrapValidator/dist/css/bootstrapValidator.css"/>
		 <!-- 图标 -->
		<link href="<%=path_home %>/plugin/icheck-1.x/skins/square/blue.css" rel="stylesheet">
		<link href="<%=path_home %>/plugin/fontawesome-4.2.0_ie7/4.2.0/css/font-awesome.css"/>
		<link href="<%=path_home %>/plugin/fontawesome-4.2.0_ie7/4.2.0/css/font-awesome-ie7.min.css"/>
		
		<!-- bootStrapTable -->
		<link rel="stylesheet" href="  <%=path_home%>/plugin/bootstrap-table-develop/src/bootstrap-table.css" >

		<!-- 表单验证 -->
		<link rel="stylesheet" href="<%=path_home%>/plugin/bootstrapValidator/dist/css/bootstrapValidator.css"/>
		
		<!-- 单选框，复选框 -->
		<link href="<%=path_home%>/plugin/icheck-1.x/skins/square/blue.css" rel="stylesheet">
		
		<!-- 日历组件 -->
		<link href="<%=path_home%>/plugin/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
		 <style>
		  .sidebar-scroll{
		  	-ms-overflow-style:none;
   			overflow:-moz-scrollbars-none;
		  }  
		 	.sidebar-scroll::-webkit-scrollbar{
			  display:none;
			}
		</style>
	</head>
	<body style="padding-right:0;overflow: auto;">
		<div id="wrapper" class="clearfix">
			<nav class="navbar navbar-default navbar-fixed-top">
			<%-- <div class="brand">
				<a><img src="<%=path_home%>/images/logo.JPG" alt="明利惠" class="img-responsive logo" style="width:50px;"></a>
			</div> --%>
			<div class="container-fluid">
				<div id="navbar-menu">
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle icon-menu" data-toggle="dropdown">
								<i class="lnr lnr-alarm"></i>
								<span class="badge bg-danger">5</span>
							</a>
							<ul class="dropdown-menu notifications">
								<li><a href="#" class="notification-item"><span class="dot bg-warning"></span>System space is almost full</a></li>
								<li><a href="#" class="notification-item"><span class="dot bg-danger"></span>You have 9 unfinished tasks</a></li>
								<li><a href="#" class="notification-item"><span class="dot bg-success"></span>Monthly report is available</a></li>
								<li><a href="#" class="notification-item"><span class="dot bg-warning"></span>Weekly meeting in 1 hour</a></li>
								<li><a href="#" class="notification-item"><span class="dot bg-success"></span>Your request has been approved</a></li>
								<li><a href="#" class="more">See all notifications</a></li>
							</ul>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="lnr lnr-question-circle"></i> <span>Help</span> <i class="icon-submenu lnr lnr-chevron-down"></i></a>
							<ul class="dropdown-menu">
								<li><a href="#">Basic Use</a></li>
								<li><a href="#">Working With Data</a></li>
								<li><a href="#">Security</a></li>
								<li><a href="#">Troubleshooting</a></li>
							</ul>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown"><img src="<%=path_home %>/image/user-medium.png" class="img-circle" alt="Avatar"> <span>Samuel</span> <i class="icon-submenu lnr lnr-chevron-down"></i></a>
							<ul class="dropdown-menu">
								<li><a href="javascript:;"><i class="lnr lnr-user"></i> <span>个人信息</span></a></li>
								<li><a href="javascript:;"><i class="lnr lnr-envelope"></i> <span>我的消息</span></a></li>
								<li><a href="javascript:;"><i class="lnr lnr-cog"></i> <span>设置</span></a></li>
								<li><a href="javascript:;" id="exits"><i class="lnr lnr-exit"></i> <span>退出</span></a></li>
							</ul>
						</li>
						<!-- <li>
							<a class="update-pro" href="#downloads/klorofil-pro-bootstrap-admin-dashboard-template/?utm_source=klorofil&utm_medium=template&utm_campaign=KlorofilPro" title="Upgrade to Pro" target="_blank"><i class="fa fa-rocket"></i> <span>UPGRADE TO PRO</span></a>
						</li> -->
					</ul>
				</div>
			</div>
		</nav>
		<!-- 左侧菜单 -->
		<div id="sidebar-nav" class="sidebar">
			<div class="sidebar-collapse" >
				<i id="sideNav1" class="fa fa-angle-double-left"></i>
			</div>
			<div id="sideNav2" href="" class=""><i class="fa fa-angle-double-right"></i></div>
			
			<div class="slimScrollDiv" style="position: relative; overflow: hidden; width:260px; height: 95%;">
				<div class="sidebar-scroll" style="overflow-y:scroll; width: 278px; height: 95%;">
					<nav>
						<ul id="sidebarTree" class="nav ztree"></ul>
					</nav>
				</div>
			</div>
		</div>
		<!--主要部分  -->
		<div class="main">
			<div class="main-content" >
				<div class="container-fluid" id="container-fluid" > 
					<ul id="tree" class="ztree"></ul>
				 </div>
			</div> 
		<div class="clearfix"></div>
		<footer>
			<div class="container-fluid">
				<p class="copyright">© 2017-2020 版权所有&nbsp;</p>
			</div>
		</footer>
	</div>
	
	<div class="">
	</div>
</body>
	<script src="<%=path_home %>/js/jquery-1.11.1.min.js"></script>
	<script src="<%=path_home %>/plugin/bootstrap/js/bootstrap.min.js"></script>
	<!-- zTree -->
	<script type="text/javascript" src="<%=path_home %>/plugin/zTree_v3-master/js/jquery.ztree.core.js"></script>
	<SCRIPT type="text/javascript" src="<%=path_home %>/js/zTree.js"></SCRIPT>
	<script type="text/javascript" src="<%=path_home %>/plugin/zTree_v3-master/js/jquery.ztree.excheck.js"></script>
	<!-- bootStrapTable -->
	<script src="<%=path_home %>/plugin/bootstrap-table-develop/dist/bootstrap-table.js"></script>
	<script src="<%=path_home %>/plugin/bootstrap-table-develop/dist/locale/bootstrap-table-zh-CN.js"></script>
	<script src="<%=path_home %>/plugin/bootstrap-table-develop/dist/extensions/export/bootstrap-table-export.js"></script>
	<!-- 表格封装 -->
	<script type="text/javascript" src="<%=path_home%>/js/jquery.widget.table.js" charset="UTF-8"></script>
	<!-- 表格处理 -->
	<script type="text/javascript" src="<%=path_home%>/js/jquery.widget.tableHandle.js" charset="UTF-8"></script>
		
	<!-- 单选框，复选框 -->
	<script src="<%=path_home %>/plugin/icheck-1.x/icheck.js"></script>
	
	 <!-- 警告框 -->
	<script src="<%=path_home%>/plugin/bootstrap-notify-master/bootstrap-notify.js"></script>
	<script src="<%=path_home%>/js/jquery.widget.notify.js"></script>
	
	<!-- 弹出框 -->
	<script type="text/javascript" src="<%=path_home%>/js/jquery.widget.dialog.js"></script>
	<script src="<%=path_home %>/js/jquery.widget.comDialog.js"></script>
	
	<!-- 按钮 -->
	<script type="text/javascript" src="<%=path_home%>/js/jquery.widget.toolBar.js"></script>
	
	<!-- 删除 -->
	<script type="text/javascript" src="<%=path_home%>/js/jquery.widget.deleteRow.js"></script>
	
	<!-- 查询 -->
	<script type="text/javascript" src="<%=path_home%>/js/jquery.widget.query.js"></script>
	
	<!-- 表单验证 -->
	<script type="text/javascript" src="<%=path_home%>/plugin/bootstrapValidator/dist/js/bootstrapValidator.js"></script>
	
	<!-- 自定义表单验证 -->
	<script type="text/javascript" src="<%=path_home%>/js/jquery.widget.bootstrapValidator.js"></script>

	<!-- 表单提交 -->
	<script type="text/javascript" src="<%=path_home%>/js/jquery.widget.formSubmit.js"></script>
	
	<!-- 日历组件 -->
	<script type="text/javascript" src="<%=path_home%>/plugin/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=path_home%>/plugin/bootstrap-datetimepicker-master/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	
	<!-- json解析 -->
	<script type="text/javascript" src="<%=path_home%>/js/json2.js" charset="UTF-8"></script>
	
	<script>
		var path = '<%=path_home%>';
	</script>

	<script>
	/* 左侧菜单栏 */
	$(document).ready(function(){
		
		var zNodes =[
			 			{ ID:1, PID:0, CN_NAME:"组件", iconSkin:"lnr lnr-home"},
			 			{ ID:11, PID:1, CN_NAME:"实例","URL":"table.jsp"},
			 			{ ID:13, PID:1, CN_NAME:"草稿"},
			 			{ ID:14, PID:1, CN_NAME:"已发送邮件"},
			 			{ ID:15, PID:1, CN_NAME:"已删除邮件"},
			 			{ ID:3, PID:0, CN_NAME:"快速视图",iconSkin:"lnr lnr-chart-bars"},
			 			{ ID:4, PID:0, CN_NAME:"测试","URL":"test.jsp"},
			 			{ ID:31, PID:3, CN_NAME:"文档"},
			 			{ ID:32, PID:3, CN_NAME:"照片","URL":"<%=path_home%>/view/template/form/cb71ac9a4b9949e4ab8e370a0a1c810d_add.jsp"},
			 			{ ID:311, PID:31, CN_NAME:"文档2"},
			 			{ ID:3111, PID:311, CN_NAME:"文档3"},
			 			{ ID:31111, PID:3111, CN_NAME:"文档4"},
			 			{ ID:5, PID:0, CN_NAME:"表管理", iconSkin:"lnr lnr-home","URL":"<%=path_home%>/table/loadTable.do"},
			 			{ ID:6, PID:0, CN_NAME:"菜单管理", iconSkin:"lnr lnr-home","URL":"<%=path_home%>/tree/loadTreeList.do"},
			 			{ ID:7, PID:0, CN_NAME:"角色管理", iconSkin:"lnr lnr-home","URL":"<%=path_home%>/roleInfo/loadRoleList.do"},
			 			{ ID:8, PID:0, CN_NAME:"用户管理", iconSkin:"lnr lnr-home","URL":"<%=path_home%>/userInfo/loadUserList.do"},
			 			{ ID:9, PID:0, CN_NAME:"机构管理", iconSkin:"lnr lnr-home","URL":"<%=path_home%>/organization/loadOrganization.do"},
			 			{ ID:12, PID:0, CN_NAME:"自定义表单",iconSkin:"lnr lnr-home","URL":"<%=path_home%>/form/loadFormList.do"}
			 		];
		
		/* 后台获取左侧菜单数据 */
		$.ajax({
			async:false,
			cache:false,
			type:'POST',  
        	dataType:"json",  
			url:'<%=path_home%>/tree/initRoleTree.do',
			success:function(data){
				zNodes = data;
			}
		});
		
		
		var treeObj = $("#sidebarTree");
		$.fn.zTree.init(treeObj, setting, zNodes);
		zTree_Menu = $.fn.zTree.getZTreeObj("sidebarTree");
		zTree_Menu.selectNode(curMenu);

		treeObj.hover(function () {
			if (!treeObj.hasClass("showIcon")) {
				treeObj.addClass("showIcon");
			}
		}, function() {
			treeObj.removeClass("showIcon");
		});
	});
	
	
	$(document).ready(function(){
	$('input.icheck').iCheck({
	    checkboxClass: 'icheckbox_square-blue',
	    radioClass: 'iradio_square-blue'
	  });
	});
	</script>
	<SCRIPT type="text/javascript" >
	var zTree;
	var demoIframe;

	var setting1 = {
		view: {
			dblClickExpand: false,
			showLine: true,
			selectedMulti: false,
			showIcon: false
		},
		data: {
			simpleData: {
				enable:true,
				idKey: "id",
				pIdKey: "pId",
				rootPId: ""
			}
		},
		callback: {
			beforeClick: function(treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("tree");
				if (treeNode.isParent) {
					zTree.expandNode(treeNode);
					return false;
				} else {
					/* demoIframe.attr("src",treeNode.file + ".html"); */
					return true;
				}
			}
		}
	};

	var zNodesList =[
		{ name:"父节点1 - 展开", open:true,
				children: [
					{ name:"父节点11 - 折叠",
						children: [
							{ name:"叶子节点111"},
							{ name:"叶子节点112"},
							{ name:"叶子节点113"},
							{ name:"叶子节点114"}
						]},
					{ name:"父节点12 - 折叠",
						children: [
							{ name:"叶子节点121"},
							{ name:"叶子节点122"},
							{ name:"叶子节点123"},
							{ name:"叶子节点124"}
						]},
					{ name:"父节点13 - 没有子节点", isParent:true}
				]},
			{ name:"父节点2 - 折叠",
				children: [
					{ name:"父节点21 - 展开", open:true,
						children: [
							{ name:"叶子节点211"},
							{ name:"叶子节点212"},
							{ name:"叶子节点213"},
							{ name:"叶子节点214"}
						]},
					{ name:"父节点22 - 折叠",
						children: [
							{ name:"叶子节点221"},
							{ name:"叶子节点222"},
							{ name:"叶子节点223"},
							{ name:"叶子节点224"}
						]},
					{ name:"父节点23 - 折叠",
						children: [
							{ name:"叶子节点231"},
							{ name:"叶子节点232"},
							{ name:"叶子节点233"},
							{ name:"叶子节点234"}
						]}
				]},
			{ name:"父节点3 - 没有子节点", isParent:true}
	];

	$(document).ready(function(){
		$.fn.zTree.init($("#tree"), setting1, zNodesList);
	});
	
	function loadContent(zUrl){
		if( typeof(zUrl)!="undefined"&& zUrl != null&& zUrl !=""){
	    	$("#container-fluid").load(zUrl);
	    }
	}
	
	//退出
	$('#exits').click(function(){
		window.location.href= "<%=path_home%>/login/exit.do";
	});
	
  </SCRIPT>
</html>

