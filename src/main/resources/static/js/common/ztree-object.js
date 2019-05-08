/**
 * ztree插件的封装
 */
(function() {

    var hiddenNodes = []; //用于存储被隐藏的结点
	var $ZTree = function(id, url) {
		this.instance = null;
		this.id = id;
		this.url = url;
		this.onClick = null;
		this.settings = null;
		this.ondblclick = null;
		this.hiddenNodes = [];
	};

	$ZTree.prototype = {
		/**
		 * 初始化ztree的设置
		 */
		initSetting : function() {
			var settings = {
				view : {
                    showLine: false,
					dblClickExpand : true,
					selectedMulti : false
				},
				data : {simpleData : {enable : true}},
                edit : { drag : {isCopy : false, isMove : false}},
				callback : {
					onClick : this.onClick,
					onDblClick:this.ondblclick
				}
			};
			return settings;
		},

		/**
		 * 手动设置ztree的设置
		 */
		setSettings : function(val) {
			this.settings = val;
		},

		/**
		 * 初始化ztree
		 */
		init : function() {
			var zNodeSeting = null;
			if(this.settings != null){
				zNodeSeting = this.settings;
			}else{
				zNodeSeting = this.initSetting();
			}
			var zNodes = this.loadNodes();
			var undefinedDevice = 0;
            zNodes.forEach(function (t) {
                switch (t.type){
                    case 'area':
                        t.ownid = t.type + '_' + t.id;
                        t.parentid = 'root_' + t.pId;
                        if(!zNodeSeting.data.simpleData.idKey || !zNodeSeting.data.simpleData.pIdKey){
                            zNodeSeting.data.simpleData.idKey = 'ownid';
                            zNodeSeting.data.simpleData.pIdKey = 'parentid';
                        }
                        break;
                    case 'device':
                        t.ownid = t.type + '_' + t.id;
                        if(t.pId==0){
                            t.parentid = 'area_999999';
                            undefinedDevice++;
						}else{
                            t.parentid = 'area_' + t.pId;
						}
                        if(!zNodeSeting.data.simpleData.idKey || !zNodeSeting.data.simpleData.pIdKey){
                            zNodeSeting.data.simpleData.idKey = 'ownid';
                            zNodeSeting.data.simpleData.pIdKey = 'parentid';
						}
                        break;
                    case 'preset':
                        t.ownid = t.type + '_' + t.id;
                        t.parentid = 'device_' + t.pId;
                        if(!zNodeSeting.data.simpleData.idKey || !zNodeSeting.data.simpleData.pIdKey){
                            zNodeSeting.data.simpleData.idKey = 'ownid';
                            zNodeSeting.data.simpleData.pIdKey = 'parentid';
                        }
                        break;
                    default: break;
                }
            });
            if(undefinedDevice>0){
				zNodes.push({
                    "name":"未分区",
                    "pId":"0",
                    "id":999999,
                    "type":"area",
					'ownid': 'area_999999',
                    'parentid': 'root_0',
					'icon': '../../../static/img/custom_ztree/area.png'
                });
			}
            this.instance = $.fn.zTree.init($("#" + this.id), zNodeSeting, zNodes);
            delete zNodeSeting.data.simpleData.idKey;
            delete zNodeSeting.data.simpleData.pIdKey;
            return this.instance;
		},
        /**
         * 初始化本地ztree
         */
		initLocal : function (zNodes) {
            var zNodeSeting = null;
            if(this.settings != null){
                zNodeSeting = this.settings;
            }else{
                zNodeSeting = this.initSetting();
            }
            var undefinedDevice = 0;
            zNodes.forEach(function (t) {
                switch (t.type){
                    case 'area':
                        t.ownid = t.type + '_' + t.id;
                        t.parentid = 'root_' + t.pId;
                        if(!zNodeSeting.data.simpleData.idKey || !zNodeSeting.data.simpleData.pIdKey){
                            zNodeSeting.data.simpleData.idKey = 'ownid';
                            zNodeSeting.data.simpleData.pIdKey = 'parentid';
                        }
                        break;
                    case 'device':
                        t.ownid = t.type + '_' + t.id;
                        if(t.pId==0){
                            t.parentid = 'area_999999';
                            undefinedDevice++;
                        }else{
                            t.parentid = 'area_' + t.pId;
                        }
                        if(!zNodeSeting.data.simpleData.idKey || !zNodeSeting.data.simpleData.pIdKey){
                            zNodeSeting.data.simpleData.idKey = 'ownid';
                            zNodeSeting.data.simpleData.pIdKey = 'parentid';
                        }
                        break;
                    case 'preset':
                        t.ownid = t.type + '_' + t.id;
                        t.parentid = 'device_' + t.pId;
                        if(!zNodeSeting.data.simpleData.idKey || !zNodeSeting.data.simpleData.pIdKey){
                            zNodeSeting.data.simpleData.idKey = 'ownid';
                            zNodeSeting.data.simpleData.pIdKey = 'parentid';
                        }
                        break;
                    default: break;
                }
            });
            if(undefinedDevice>0){
                zNodes.push({
                    "name":"未分区",
                    "pId":"0",
                    "id":999999,
                    "type":"area",
                    'ownid': 'area_999999',
                    'parentid': 'root_0',
                    'icon': '../../../static/img/custom_ztree/area.png'
                });
            }
            this.instance = $.fn.zTree.init($("#" + this.id), zNodeSeting, zNodes);
            return this.instance;
        },
		/**
		 * 绑定onclick事件
		 */
		bindOnClick : function(func) {
			this.onClick = func;
		},
		/**
		 * 绑定双击事件
		 */
		bindOnDblClick : function(func) {
			this.ondblclick=func;
		},


		/**
		 * 加载节点
		 */
		loadNodes : function() {
			var zNodes = null;
			var ajax = new $ax(Feng.ctxPath + this.url, function(data) {
				zNodes = data;
			}, function(data) {
				Feng.error("加载ztree信息失败!");
			});
			ajax.start();
			return zNodes;
		},

		/**
		 * 获取选中的值
		 */
		getSelectedVal : function(){
			var zTree = $.fn.zTree.getZTreeObj(this.id);
			var nodes = zTree.getSelectedNodes();
			return nodes[0].name;
		},
        // filter: function (ztreeObj, _prop, _keyword) {
        // 	$('.node_name').css('color', '#333');
        // 	if(!_keyword){
        // 		return;
        // }
        //     var nodes = ztreeObj.getNodesByParamFuzzy(_prop, _keyword);
        //     nodes.forEach(function (item) {
        // 	if(item.level==1){
        //             ztreeObj.expandNode(item.getParentNode(), true, false, false);
        //             $("#" + item.tId + "_span").css('color', 'red');
        // 	}
        //     });
        // },
        /**
         * ztree节点过滤
         */
		filter: function (ztreeObj, _keyword) {
			//显示上次搜索后背隐藏的结点
            ztreeObj.showNodes(hiddenNodes);
            //查找不符合条件的叶子节点(层级为1，且名字不包含自定字段的节点)
            function filterFunc(node){
                if(node.level!=1||node.name.indexOf(_keyword)!=-1) {
                    if(node.level==0){
                        ztreeObj.expandNode(node, true, false, false);
                    }
                	return false;
                }
                return true;
            };

            //获取不符合条件的叶子结点
			if(_keyword){
                hiddenNodes = ztreeObj.getNodesByFilter(filterFunc);
                //隐藏不符合条件的叶子结点
                ztreeObj.hideNodes(hiddenNodes);
			}else{
                hiddenNodes = [];
                ztreeObj.expandAll(false);
			}

        }
	};

	window.$ZTree = $ZTree;

}());