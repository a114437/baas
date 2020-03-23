/**
 * 作者:鲁
 * 作用：左侧菜单缩放
 */
/*$("#btn-toggle-fullwidth").click(function(){
		$("body").toggleClass("layout-fullwidth offcanvas-active");
		if($(this).children().hasClass("lnr lnr-arrow-left-circle")){
			$(this).children().removeClass("lnr lnr-arrow-left-circle");
			$(this).children().addClass("lnr lnr-arrow-right-circle");
		} else{
			$(this).children().removeClass("lnr lnr-arrow-right-circle");
			$(this).children().addClass("lnr lnr-arrow-left-circle");
		}
});*/
$(document).on("click","#sideNav1",function(e){
	$("body").toggleClass("layout-fullwidth offcanvas-active");
	$("#sideNav2").show(500);
});
$(document).on("click","#sideNav2",function(){
	$("body").toggleClass("layout-fullwidth offcanvas-active");
	$("#sideNav2").hide(500);
});


