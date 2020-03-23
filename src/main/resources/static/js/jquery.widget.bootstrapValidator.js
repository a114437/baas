/**
 * bootstrapValidator自定义验证规则
 * lu
 * 2017/7/27
 */
/*非空：notNull
	 *手机号：mobile
	 *邮政编码：isZipCode
	 *数字：number
	 *邮箱：mail
	 *电话：phone
	 *区号：ac
	 *无区号电话：noactel
	 *邮箱或手机：mm
	 *电话或手机：tm
	 *年龄：age
	 *传真：fax
	 *汉字：chinese
	 *身份证：idCard 
	 *指定数字的整数倍：times,定义data-rule-times的值 
	 *小于指定的数值：lt, 定义data-bv-lt-value的值
	 *大于指定的数：gt，定义data-bv-gt-value的值
	 *大写：capital
	 *小写：lowercase 
	 *英文：EngLish
	 *验证java命名规范：defineName
	 * */
;(function($) {  
	//不为空
	 $.fn.bootstrapValidator.validators.notNull = {  
	            validate: function(validator, $field, options) {  
	                var value = $field.val(); 
	                var returnVal = true;
	                if(value==""){
	                    returnVal=false;
	                }
	                return { valid :returnVal, message: '不能为空'};
	            }  
	        }; 
    
    //邮政编码
    $.fn.bootstrapValidator.validators.isZipCode = {  
            validate: function(validator, $field, options) {  
                var value = $field.val();  
                var  code=/^[0-9]{6}$/;
                return { valid :code.test( value ), message: '请正确填写您的邮政编码'};
            }  
        }; 
    //数字
    $.fn.bootstrapValidator.validators.number = {  
            validate: function(validator, $field, options) {  
                var value = $field.val();  
                var num= /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/;
                return { valid :num.test( value ), message: '请输入有效的数字'};
            }  
        }; 
     
    //整数
     $.fn.bootstrapValidator.validators.intnumber = {  
            validate: function(validator, $field, options) {  
                var value = $field.val();  
                var num=/^[0-9]*$/;
                return { valid :num.test( value ), message: '请输入有效的数字'};
            }  
    };    
        
        
    //邮箱
    $.fn.bootstrapValidator.validators.mail = {  
            validate: function(validator, $field, options) {  
                var value = $field.val();  
                var mail = /^[a-z0-9._%-]+@([a-z0-9-]+\.)+[a-z]{2,4}$/;
                return { valid :mail.test( value ), message: '邮箱格式不对'};
            }  
        }; 
  //电话验证规则
    $.fn.bootstrapValidator.validators.phone = {  
            validate: function(validator, $field, options) {  
                var value = $field.val();  
                var phone = /^0\d{2,3}-\d{7,8}$/;
                return { valid :phone.test( value ), message: '电话格式如：0371-68787027'};
            }  
        }; 
  //区号验证规则
    $.fn.bootstrapValidator.validators.ac = {  
            validate: function(validator, $field, options) {  
                var value = $field.val();  
                var ac = /^0\d{2,3}$/;
                return { valid :ac.test( value ), message: '区号如：010或0371'};
            }  
        }; 
  //无区号电话验证规则
    $.fn.bootstrapValidator.validators.noactel = {  
            validate: function(validator, $field, options) {  
                var value = $field.val();  
                var noactel= /^\d{7,8}$/;
                return { valid :noactel.test( value ), message: '电话格式如：68787027'};
            }  
        }; 
    //手机号验证 
    $.fn.bootstrapValidator.validators.mobile = {  
        validate: function(validator, $field, options) {  
            var value = $field.val();  
            var mobile = /^1[3|4|5|7|8]\d{9}$/;
            return { valid :mobile.test( value ), message: '请输入正确的手机号'};
        }  
    };
  //邮箱或手机验证规则
    $.fn.bootstrapValidator.validators.mm = {  
            validate: function(validator, $field, options) {  
                var value = $field.val();  
                var mm = /^[a-z0-9._%-]+@([a-z0-9-]+\.)+[a-z]{2,4}$|^1[3|4|5|7|8]\d{9}$/;
                return { valid :mm.test( value ), message: '格式不对'};
            }  
        }; 
  //电话或手机验证规则
    $.fn.bootstrapValidator.validators.tm = {  
            validate: function(validator, $field, options) {  
                var value = $field.val();  
                var tm=/(^1[3|4|5|7|8]\d{9}$)|(^\d{3,4}-\d{7,8}$)|(^\d{7,8}$)|(^\d{3,4}-\d{7,8}-\d{1,4}$)|(^\d{7,8}-\d{1,4}$)/;
                return { valid :tm.test( value ), message: '格式不对'};
            }  
        }; 
  //年龄
    $.fn.bootstrapValidator.validators.age = {  
            validate: function(validator, $field, options) {  
                var value = $field.val();  
                var age = /^(?:[1-9][0-9]?|1[01][0-9]|120)$/;
                return { valid :age.test( value ), message: '年龄范围在0-120之间'};
            }  
        }; 
  //传真
    $.fn.bootstrapValidator.validators.fax = {  
            validate: function(validator, $field, options) {  
                var value = $field.val();  
                var fax = /^(\d{3,4})?[-]?\d{7,8}$/;
                return { valid :fax.test( value ), message: '传真格式如：0371-68787027'};
            }  
        };  
  //汉字
    $.fn.bootstrapValidator.validators.chinese = {  
            validate: function(validator, $field, options) {  
                var value = $field.val();  
                var chinese= /^[\u4E00-\u9FFF]+$/;
                return { valid :chinese.test( value ), message: '请输入中文'};
            }  
        }; 
  //身份证
    $.fn.bootstrapValidator.validators.idCard = {  
            validate: function(validator, $field, options) {  
                var value = $field.val();  
                var isIDCard1=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;//(15位)
            	var isIDCard2=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;//(18位)
                return { valid :(isIDCard1.test( value )||isIDCard2.test( value ) ), message: '请输入正确的身份证号'};
            }  
        }; 
  //指定数字的整数倍
    $.fn.bootstrapValidator.validators.times = {  
            validate: function(validator, $field, options) {
                var value = $field.val();  
                var returnVal = true;
                var base=$field.attr('data-rule-times');
                if(value%base!=0){
                    returnVal=false;
                }
                return { valid :returnVal, message: '请输入'+base+'的整数倍'};
            }  
        }; 
    //小于 data-bv-lt-value指定的数值
    $.fn.bootstrapValidator.validators.lt = {  
            validate: function(validator, $field, options) {
                var value = $field.val();  
                var returnVal = true;
                var lessVal=$field.attr('data-bv-lt-value');
                if(value>lessVal){
                    returnVal=false;
                }
                return { valid :returnVal, message: '请输入小于'+lessVal+'的数'};
            }  
        };
    
  //大于data-bv-gt-value指定的数
    $.fn.bootstrapValidator.validators.gt = {  
            validate: function(validator, $field, options) {
                var value = $field.val();  
                var returnVal = true;
                var gtVal=$field.attr('data-bv-gt-value');
                if(gtVal>value){
                    returnVal=false;
                }
                return { valid :returnVal, message: '请输入大于'+gtVal+'的数'};
            }  
        };
    
    //大写
    $.fn.bootstrapValidator.validators.capital = {  
            validate: function(validator, $field, options) {  
                var value = $field.val();  
                var capital= /^[A-Z]+$/;//(15位)
                return { valid :(capital.test( value ) ), message: '请输入大写字母'};
            }  
        }; 
  //小写
    $.fn.bootstrapValidator.validators.lowercase = {  
            validate: function(validator, $field, options) {  
                var value = $field.val();  
                var lowercase= /^[a-z]+$/;//(15位)
                return { valid :(lowercase.test( value ) ), message: '请输入小写字母'};
            }  
        };
    
    //英文
    $.fn.bootstrapValidator.validators.EngLish = {  
            validate: function(validator, $field, options) {  
                var value = $field.val();  
                var EngLish= /^[A-Za-z]+$/;//英文字母不区分大小写
                return { valid :(EngLish.test( value ) ), message: '请输入英文字母'};
            }  
        };   
   //验证java命名规范
    $.fn.bootstrapValidator.validators.defineName = {  
            validate: function(validator, $field, options) {  
                var str = $field.val();
                var returnTag=false;
                var message="";//提示
               
                var tagTest=/^[a-z\d\._.$.-]+$/i ;//匹配字母数字，下划线和$
                var numTop=/^[0-9]+/;//匹配数字开头(验证不通过)
                var numAll=/^[0-9]*$/;//全部为数字
                var symbol=/[^\_$]/;//字符串包$和下划线，不能只有$和下划线
                var complex = 0;
                if(str!=""){
                    if (tagTest.test(str)) {
                    	returnTag=true;
                    }else{
                    	returnTag=false;
                    	message="只能输入数字、英文、下划线、$";
                    }
                    
                    if (numTop.test(str)) {//开头为数字
                    	returnTag=false;
                    	message="不能以数字开头";
                    }
                    
                    if (numAll.test(str)) {//全部为数字
                    	returnTag=false;
                    	message="不能全部为数字";
                    }
                    if(!symbol.test(str)) {//字符串包$和下划线，不能只有$和下划线
                    	returnTag=false;
                    	message="不能只有$和下划线";
                    }
                }
                
                return { valid :returnTag, message: message};
            }  
        };
}(window.jQuery));