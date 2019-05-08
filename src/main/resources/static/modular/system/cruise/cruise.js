var baseUrl = '';
var CruiseMager = {
    id: 'cruise_table', //巡航路线表格id
    tableParams: {'uniqueId': 'id'},
    table: null,
    setItem: null, //选中行数据对象
    setItemIndex: -1, //选中行index
    planState: false,  //巡航计划编辑状态记录
    layerIndex: -1
};
/**
 * 自定义表格列
 */
CruiseMager.customIndexCell = function (value, rowData, index) {
    return index;
};
CruiseMager.customTimeCell = function (value, rowData, index) {
    var text = "";
    var timerange = value.split(' ');
    var time = timerange[1]+":"+timerange[0];
    switch (timerange[5]){
        case '*': text = '每天，'+ time + '，持续:' + timerange[timerange.length-1] + '分钟';
            break;
        case '1/5': text = '周一至周五，'+ time + '，持续:' + timerange[timerange.length-1] + '分钟';
            break;
        default:  text = '自定义，'+ time + '，持续:' + timerange[timerange.length-1] + '分钟';
            break;
    }
    return text;
};
CruiseMager.customStatusCell = function (value, rowData, index) {
    var initStatus = "";
    if(value){
        initStatus = "checked";
    }
    return '<div class="switch switch-small">' +
        '<input type="checkbox" '+initStatus+' name="cruise-status" />' +
        '</div>';
};
CruiseMager.customOperCell = function (value, rowData, index) {
    return '<button type="button" class="btn btn-primary btn-xs cruise-edit" style="margin-bottom: 0;">修改</button>' +
        '<button type="button" class="btn btn-danger btn-xs cruise-delete" style="margin-left: 5px;margin-bottom: 0;">删除</button>' +
        '<button type="button" class="btn btn-primary btn-xs cruise-configure" style="margin-left: 5px;margin-bottom: 0;">配置</button>';
    // '<button type="button" class="btn btn-primary btn-xs cruise-apply" style="margin-left: 5px;margin-bottom: 0;">应用</button>';
};
/**
 * 表格事件
 */
window.cruiseEvents = {
    /**
     * 编辑巡航计划
     */
    'click .cruise-edit': function (e, value, row, index) {
        var callback = function () {
            $('#planname').val(row.planname);
            $('#plandescription').val(row.plandescription);
            $('input[value="'+row.using+'"]').prop('checked', true);
            $('.time_picker').timepicker({
                showMeridian: false,
                minuteStep: 1,
                disableFocus: true,
                // showSeconds: true,
                // secondStep: 1
            });
            var timerange = row.timerange.split('-');
            $('#start-time').val(timerange[0]);
            $('#end-time').val(timerange[1]);
            CruiseMager.setItem = row;
            CruiseMager.setItemIndex = index;
            CruiseMager.planState = true;
        };
        CruiseMager.openLayer('addCruisePlan', '编辑计划', '500px', null, callback);
    },
    /**
     * 删除巡航计划
     */
    'click .cruise-delete':function (e, value, row, index) {
        var oper = function () {
            var ids = [];
            ids.push(row.id);
            var ajax = new $ax(baseUrl + "/cruiseplan/plandel", function (data) {
                Feng.success("删除成功");
                $('#'+CruiseMager.id).bootstrapTable('remove', {field: 'id', values: ids});
            }, function (data) {
                Feng.error("删除失败");
            });
            ajax.set('planid', row.id);
            ajax.start();
        };
        Feng.confirm('是否删除巡航计划:'+row.planname+'？', oper);
    },
    /**
     * 配置巡航计划
     */
    'click .cruise-configure':function (e, value, row, index) {
        var width = document.body.clientWidth * 0.8 + 'px';
        var height = document.body.clientHeight * 0.8 + 'px';
        CruiseDevice.planid = row.id;
        CruiseMager.openLayer('configueDevice', '配置巡航计划', width, height, CruiseDevice.initDevices);
    },
    // /**
    //  * 应用巡航计划
    //  */
    // 'click .cruise-apply':function (e, value, row, index) {
    //     var oper = function () {
    //         var ajax = new $ax(baseUrl + "/cruiseplan", function (data) {
    //             Feng.success("应用成功");
    //         }, function (data) {
    //             Feng.error("应用失败");
    //         });
    //         ajax.set('planid', row.id);
    //         ajax.start();
    //     };
    //     Feng.confirm('是否应用巡航计划:'+row.planname+'？', oper);
    // }
};
/**
 * 表格字段
 */
CruiseMager.columns = [
    {field: 'selectItem', radio: true},
    {field: 'rowindex', class: 'hidden', formatter:CruiseMager.customIndexCell},
    {title: 'ID', field: 'id', align: 'center', valign: 'middle', sortable: true, width: '50px'},
    {title: '巡航计划名称', field: 'planname', align: 'center', valign: 'middle', sortable: true},
    {title: '描述', field: 'plandescription', align: 'center', valign: 'middle', sortable: true},
    {title: '时间段', field: 'timerange', align: 'center', valign: 'middle', sortable: true, width:"20%"},
    {title: '状态', field: 'using', align: 'center', valign: 'middle', sortable: true, width: '150px', formatter:CruiseMager.customStatusCell},
    {title: '操作', field: 'oper', align: 'center', valign: 'middle', sortable: true, width: '200px',formatter: CruiseMager.customOperCell, events: cruiseEvents}
];
/**
 * 检查是否选中
 */
CruiseMager.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info('请选择巡航计划');
        return false;
    } else {
        this.setItem = selected[0];
        return true;
    }
};
/**
 * 打开弹出层
 */
CruiseMager.openLayer = function (id, title, width, height, callback) {
    width = width ? width : 'auto';
    height = height ? height : 'auto';
    CruiseMager.layerIndex = layer.open({
        type: 1,
        title: title,
        area: [width, height],
        fix: false,
        maxmin: true,
        content: $('#'+id).html(),
        success: function (layero, index) {
            if(callback){
                callback($(layero).height());
            }
        },
        end: function () {
            if(CruiseMager.setItem) {
                CruiseMager.setItem = null;
                CruiseMager.setItemIndex = -1;
            }
            if(CruiseMager.planState){
                CruiseMager.planState = false;
            }
            if(CruiseDevice.planid) {
                CruiseDevice.planid = -1;
            }
        }
    });
};
/**
 * 添加巡航计划
 */
CruiseMager.addPlan = function (event) {
    var callback = function () {
        $('.time_picker').timepicker({
            showMeridian: false,
            minuteStep: 1,
            disableFocus: true
            // showSeconds: true,
            // secondStep: 1
        });
    };
    this.openLayer('addCruisePlan', '添加计划', '500px', null, callback);
};
/**
 * 获取计划数据
 */
function standardTime(time) {
    var time = time.split(':');
    if(parseInt(time[0])<10){
        time[0] = '0' + parseInt(time[0]);
    }
    return time.join(':');
}
CruiseMager.getPlan = function (id) {
    var planname = $('#planname').val(), plandescription = $('#plandescription').val(), start_time = $('#start-time').val(), end_time = $('#end-time').val(), cycle = "", timerange = "";
    var status = $($('input[name="curise-status"]:checked')[0]).prop('value');
    if(!planname) {
        Feng.error('请输入计划名称');
        return false;
    }
    if(!start_time) {
        Feng.error('请选择开始时间');
        return false;
    }
    if(!end_time) {
        Feng.error('请选择结束时间');
        return false;
    }
    start_time = standardTime(start_time);
    end_time = standardTime(end_time);
    if(start_time>end_time){
        Feng.error('时间段无效');
        return false;
    }
    timerange = start_time + '-' + end_time;
    if(status=='1' && !CruiseMager.checkTimeOverlap(timerange, id)){
        Feng.error('与已启用计划时间重叠');
        return false;
    }
    var planData = {
        planname: planname,
        plandescription: plandescription,
        using: parseInt(status),
        timerange: timerange
    };
    return planData;
};
/**
 * 保存巡航计划
 */
CruiseMager.savePlan = function () {
    var url = "" ,tips ="", planData = null;
    if(CruiseMager.planState){
        planData = this.getPlan(CruiseMager.setItem.id);
    }else{
        planData = this.getPlan();
    }
    if(!planData) {
        return;
    }
    if(CruiseMager.setItem) {
        planData.id = CruiseMager.setItem.id;
        url = baseUrl + "/cruiseplan/planupdate";
        tips = "修改";
    }else{
        url = baseUrl + "/cruiseplan/planadd";
        tips = "添加";
    }
    var ajax = new $ax(url, function (data) {
        Feng.success(tips+"成功");
        layer.close(CruiseMager.layerIndex);
        planData.name = planData.planname;
        if(planData.id){
            //编辑
            CruiseMager.setItem = $.extend(CruiseMager.setItem, planData);
            $('#'+CruiseMager.id).bootstrapTable('updateRow', {index: CruiseMager.setItemIndex, row: CruiseMager.setItem});
        }else{
            //添加
            planData.id = data.message;
            $('#'+CruiseMager.id).bootstrapTable('append', planData);
        }
    }, function (data) {
        Feng.error(tips+"失败");
    });
    ajax.set(planData);
    ajax.start();
};
/**
 * 复制巡航计划
 */
CruiseMager.plancopy = function () {
    if(this.check()) {
        var oper = function () {
            var param = {
                sourceplanid: CruiseMager.setItem.id,
                planname: CruiseMager.setItem.planname,
                plandescription: CruiseMager.setItem.plandescription,
                using: CruiseMager.setItem.using,
                timerange: CruiseMager.setItem.timerange
            };
            var ajax = new $ax(baseUrl + "/cruiseplan/duplicateplan", function (data) {
                Feng.success("复制成功");
                param.id = data.message;
                $('#'+CruiseMager.id).bootstrapTable('append', param);
                CruiseMager.setItem = null;
            }, function (data) {
                Feng.error("复制失败");
            });
            ajax.set(param);
            ajax.start();
        };
        Feng.confirm('是否复制当前选择巡航计划？', oper);
    }
};
/**
 * 全局配置
 */
CruiseMager.overConfig = function () {
    if(this.check()) {
        this.openLayer('overConfig', '全局配置', '500px', null);
    }
};
/**
 * 保存全局配置
 */
CruiseMager.saveConfig = function () {
    var defaultTime = $("#defaultTime").val();
    var defaultSpeed = $("#defaultSpeed").val();
    if(!defaultTime){
        Feng.info('请输入默认时间');
        return;
    }
    if(!defaultSpeed){
        Feng.info('请输入默认速度');
        return;
    }
    var ajax = new $ax(baseUrl + "/cruiseplan/set_default_values", function (data) {
        layer.close(CruiseMager.layerIndex);
    }, function (data) {
        Feng.error("复制失败");
    });
    ajax.set({planid: CruiseMager.setItem.id, residence: defaultTime, speed: defaultSpeed});
    ajax.start();
};
/**
 * 检查时间重叠
 */
CruiseMager.checkTimeOverlap = function (currentRange, id) {
    var item_timerange, item_start, item_end, result = true;
    var current_timerange = currentRange.split('-');
    var current_start = current_timerange[0];
    var current_end = current_timerange[1];
    var plans = $('#'+CruiseMager.id).bootstrapTable('getData');
    for(var i=0;i<plans.length;i++){
        if(plans[i].id!=id && plans[i].using){
            item_timerange = plans[i].timerange.split('-');
            item_start = item_timerange[0];
            item_end = item_timerange[1];
            if((current_start<item_start && current_end>item_start) || (current_start<item_end && current_end>item_end) || (current_start>item_start && current_end<item_end)){
                result = false;
                break;
            }
        }
    }
    return result;
};
/**
 * 修改巡航计划状态
 */
CruiseMager.stateChange = function (event, state) {
    var target = event.target, tips = "";
    var $tr = $(target).closest('tr');
    var rowIndex = $tr.find('td:eq(1)').text();
    var planid = $tr.find('td:eq(2)').text();
    if(state){
        tips = "启用";
        var currentItem = $('#'+CruiseMager.id).bootstrapTable('getRowByUniqueId', planid);
        if(!CruiseMager.checkTimeOverlap(currentItem.timerange, planid)){
            Feng.error('与已启用计划时间重叠');
            $(target).bootstrapSwitch('state', !state);
            event.stopImmediatePropagation();
            return;
        }
    }else{
        tips = "禁用";
    }
    var param = {id: planid, using: +state};
    var ajax = new $ax(baseUrl + "/cruiseplan/planupdate", function (data) {
        // Feng.success(tips + "成功");
        $('#'+CruiseMager.id).bootstrapTable('updateCell', {index: rowIndex, field: 'using',value: +state});
    }, function (data) {
        Feng.error(tips + "失败");
        $(target).bootstrapSwitch('state', !state);
    });
    ajax.set(param);
    ajax.start();
};
/**
 * 导出计划ini文件
 */
CruiseMager.export = function (event) {
    $(event.target).attr('href', "##");
    if(this.check()) {
        var url = baseUrl + '/cruiseplan/export?planid=' + this.setItem.id;
        $(event.target).attr('href', url);
        return true;
    }else{
        return false;
    }
};
/**
 * 应用巡航计划
 */
CruiseMager.applyCruise = function () {
    var oper = function () {
        new $ax(baseUrl + "/cruiseplan/cruiseapply", function (data) {
            Feng.success("应用成功");
        }, function (data) {
            Feng.error("应用失败");
        }).start();

    };
    Feng.confirm('是否应用所有巡航计划？', oper);
}
/**
 * 表格自定义回调
 */
CruiseMager.tableParams.onPostBody = function () {
    var s = $('input[name="cruise-status"]').bootstrapSwitch({
        onText: "启用",
        offText: "禁用",
        onColor: "success",
        offColor: "danger",
        size: "mini",
        onSwitchChange: CruiseMager.stateChange,
    });
};
$(function () {
    var table = new BSTable(CruiseMager.id, baseUrl+'/cruiseplan/planlist', CruiseMager.columns, CruiseMager.tableParams);
    table.setPaginationType("client");
    CruiseMager.table = table.init();
});