package com.web.frame.util;

import org.springframework.stereotype.Component;

//18位公民身份证号码是特征组合，由17位数字本体码和1位数字校验码组成。
//排列顺序从左至右以此为：6位数字地址码、8位数字出生日期码、3位数字顺序码和1位数字校验码。
//地址码：表示编码对象常住户口所在县（市、旗、区）的行政区划代码，按GB/T 2260的规定执行。
//出生日期码：表示编码对象出生的年、月、日，GB/T 7408的规定执行。年、月、日代码之间不用分割符。
//顺序码：表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号。
//顺序号的奇数分配给男性，偶数分配给女性。
//校验码：校验码采用ISO 7064:1983.MOD 11-2校验码系统。
//求校验码方法：对前17位数字本体码加权求和，公式为：S=Sum(Ai*Wi),i=0,1,...,16,
//其中Ai表示第i位置上的身份证号号码数字值，Wi表示第i位置上的加权因子，
//加权因子的值从0到16位分别为7、9、10、5、8、4、2、1、6、3、7、9、10、5、8、4、2
//对S取摸，公式为：Y=mod(S,11)。
//通过摸Y得到对应的校验码，对应关系为<0,1>、<1,0>、<2,X>、<3,9>、<4、8>、<5、7>、<6、6>、<7，5>、<8,4>、<9,3>、<10,2>
//关系中前者为Y的值，后者为对应的校验码。
@Component
public class IDCard {

  //加权因子
  private static final int[] weight = new int[]{7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1};
  //校验码
  private static final int[] checkDigit = new int[]{1,0,'X',9,8,7,6,5,4,3,2};

  public IDCard(){
  }

  private String getCheckDigit(String eighteeCardId){
      int remaining = 0;
      if(eighteeCardId.length()==18){
          eighteeCardId = eighteeCardId.substring(0,17);
      }

      if (eighteeCardId.length()==17){
          int sum = 0;
          int [] a = new int[17];
          for(int i=0;i<17;i++){
              String k = eighteeCardId.substring(i,i+1);
              a[i] = Integer.parseInt(k);
          }
          for(int i=0;i<17;i++){
              sum = sum +weight[i]*a[i];
          }

          remaining = sum %11;
      }

      return remaining==2 ? "X" :String.valueOf(checkDigit[remaining]);
  }

  private String update2eighteen(String fifteenCardId){
      String eighteenCardId = fifteenCardId.substring(0,6);
      eighteenCardId = eighteenCardId+"19";
      eighteenCardId = eighteenCardId+fifteenCardId.substring(6,15);
      eighteenCardId = eighteenCardId+this.getCheckDigit(eighteenCardId);
      return eighteenCardId;
  }

  public boolean verify(String idcard){
      if(idcard.length()==15){
          idcard=this.update2eighteen(idcard);
      }
      if (idcard.length()!=18){
          return false;
      }

      String checkDigit = idcard.substring(17,18);
      if(checkDigit.equals(this.getCheckDigit(idcard))){
          return true;
      }

      return false;
  }

  public static void main(String[] args){
      IDCard idCard = new IDCard();
      String idCardStr = "142621199308063533";
      System.out.println("身份证:"+idCardStr+"验证合格?："+idCard.verify(idCardStr));

      idCardStr = "320311770706002";
      System.out.println("身份证:"+idCardStr+"验证合格?："+idCard.verify(idCardStr));

  }
}