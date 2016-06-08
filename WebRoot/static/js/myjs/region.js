/**
 * 省市区下拉框change事件
 * @param selectId        当前选择的selectId
 * @param changeSelectId  需要级联修改的目标selectId
 * @param emptySelectId   需要清空的目标selectId
 * @param type            类型  province：省；city：市；area:区
 */
function changeRegion(selectId,changeSelectId,emptySelectId,type) {
	//获得当前选择的select的选中值
	var regionParentId = $("#"+selectId).val();
	//当需要清空的目标selectId不为空，则清空目标select选项
	if(null != emptySelectId && '' != emptySelectId) {
		var emptyOption = [];
		$('#'+emptySelectId).html(emptyOption.join(''));
		$('#'+emptySelectId).trigger('liszt:updated');
	}
	//根据当前选择的select的值，动态加载需要级联的目标select数据
	var citySelectValue = '';
	$.ajax({
		async:false,
		type: "POST",
		url: local + 'region/queryRegionByParentId.do',
    	data: {
    		regionParentId : regionParentId,
			tm:new Date().getTime()
		},
		dataType:'json',
		cache: false,
		success: function(data){
			if(null != data && "" != data){
				var option = [];
				$.each(data,function(index,item){
					if(index == 0) {
						citySelectValue = item.regionId;
					}
					option.push("<option value='"+item.regionId+"'>",item.regionName,"</option>");
				});
				$('#'+changeSelectId).html(option.join(''));
				$('#'+changeSelectId).trigger('liszt:updated');
			}
		}
	});
	
	//当type=province时，选中第一个城市，加载区数据
	if(type == 'province') {
		$.ajax({
			type: "POST",
			url: local + 'region/queryRegionByParentId.do',
	    	data: {
	    		regionParentId : citySelectValue,
				tm:new Date().getTime()
			},
			dataType:'json',
			cache: false,
			success: function(data){
				if(null != data && "" != data){
					var option = [];
					$.each(data,function(index,item){
						option.push("<option value='"+item.regionId+"'>",item.regionName,"</option>");
					});
					$('#'+emptySelectId).html(option.join(''));
					$('#'+emptySelectId).trigger('liszt:updated');
				}
			}
		});
	}
}

/**
 * 校验省市区数据是否选择
 */
function checkRegion(provinceId,cityId,areaId) {
	var selectProvinceValue = $('#'+provinceId).val();
	var selectCityValue = $('#'+cityId).val();
	var selectAreaValue = $('#'+areaId).val();
	//alert("省："+selectProvinceValue+"；市："+selectCityValue+"；区："+selectAreaValue);
	if(null == selectProvinceValue || "" == selectProvinceValue || 0 == selectProvinceValue) {
		$("#"+provinceId).tips({
			side:3,
            msg:'请选择省份',
            bg:'#FF0000',
            time:1.5
        });
		return false;
	}
	if(null == selectCityValue || "" == selectCityValue || 0 == selectCityValue) {
		$("#"+cityId).tips({
			side:3,
            msg:'请选择城市',
            bg:'#FF0000',
            time:1.5
        });
		return false;
	}
	if(null == selectAreaValue || "" == selectAreaValue || 0 == selectAreaValue) {
		$("#"+areaId).tips({
			side:3,
            msg:'请选择辖区',
            bg:'#FF0000',
            time:1.5
        });
		return false;
	}
	return true;
}