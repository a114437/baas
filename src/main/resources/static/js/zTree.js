var curMenu = null, zTree_Menu = null;
		var setting = {//配置设置
			view: {
				showLine: false,
				showIcon: false,
				selectedMulti: false,
				dblClickExpand: false,
				addDiyDom: addDiyDom
			},
			data: {
				simpleData:{
					enable:true,
					idKey:"id",
					pIdKey:"pid"
				},
				key:{
					name:"name",
					url:"url"
				}
			},
			callback: {
				beforeClick: beforeClick,
				onExpand: onExpand,
				onClick: this.onClick
			}
		};

		function addDiyDom(treeId, treeNode) {
			var spaceWidth = 5;
			var switchObj = $("#" + treeNode.tId + "_switch"),
			icoObj = $("#" + treeNode.tId + "_ico");
			switchObj.remove();
			icoObj.before(switchObj);

			if (treeNode.level > 1) {
				var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
				switchObj.before(spaceStr);
			}
		}
		
		var curExpandNode = null;
		function beforeClick(treeId, treeNode) {
			/*处理点击样式*/
			$("#"+treeId).find(".active").removeClass("active");
			/*给level0添加样式*/
			if(treeNode.level!=0){
	        	var parent=getParent(treeNode);
	        	var nodeId=parent.tId;
	        	$("#"+nodeId+"_a").addClass("active");
			}
			
			var pNode = curExpandNode ? curExpandNode.getParentNode():null;
			var treeNodeP = treeNode.parentTId ? treeNode.getParentNode():null;
			if (treeNode.children) {
				var zTree = $.fn.zTree.getZTreeObj("sidebarTree");
				zTree.expandNode(treeNode);
			}
			while (pNode) {
				if (pNode === treeNode) {
					break;
				}
				pNode = pNode.getParentNode();
			}
			if (!pNode) {
				singlePath(treeNode);
			}
			return true;
		}

		
		function singlePath(newNode) {
			if (newNode === curExpandNode) return;

            var zTree = $.fn.zTree.getZTreeObj("sidebarTree"),
                    rootNodes, tmpRoot, tmpTId, i, j, n;

            if (!curExpandNode) {
                tmpRoot = newNode;
                while (tmpRoot) {
                    tmpTId = tmpRoot.tId;
                    tmpRoot = tmpRoot.getParentNode();
                }
                rootNodes = zTree.getNodes();
                for (i=0, j=rootNodes.length; i<j; i++) {
                    n = rootNodes[i];
                    if (n.tId != tmpTId) {
                        zTree.expandNode(n, false);
                    }
                }
            } else if (curExpandNode && curExpandNode.open) {
				if (newNode.parentTId === curExpandNode.parentTId) {
					zTree.expandNode(curExpandNode, false);
				} else {
					var newParents = [];
					while (newNode) {
						newNode = newNode.getParentNode();
						if (newNode === curExpandNode) {
							newParents = null;
							break;
						} else if (newNode) {
							newParents.push(newNode);
						}
					}
					if (newParents!=null) {
						var oldNode = curExpandNode;
						var oldParents = [];
						while (oldNode) {
							oldNode = oldNode.getParentNode();
							if (oldNode) {
								oldParents.push(oldNode);
							}
						}
						if (newParents.length>0) {
							zTree.expandNode(oldParents[Math.abs(oldParents.length-newParents.length)-1], false);
						} else {
							zTree.expandNode(oldParents[oldParents.length-1], false);
						}
					}
				}
			}
			curExpandNode = newNode;
		}

		function onExpand(event, treeId, treeNode) {
			curExpandNode = treeNode;
		}
		
		
		
		//节点点击事件
		function onClick(e,treeId,node){
			
	    //	var zDepId = node.id;
	    //  var zDepId = node.pId;
	    	var id = node.id;
	        var zUrl = node.URL;
	        var zCn = node.name;
	        var zIcon = node.iconSkin;
	        var pId = node.pid;
			//调用父页面的createTab选项卡函数加载页面
	        if( typeof(zUrl)!="undefined"&& zUrl != null&& zUrl !=""){
	        	$("#container-fluid").load(zUrl);
	        }
	    }
		
		function getParent(node){
			var that=node;
			var newNode={};
			if(node.getParentNode().level>0){
				return getParent(that.getParentNode());
			}else{
				newNode=that.getParentNode();
				return newNode;
			}
			return false;
		}
		
		