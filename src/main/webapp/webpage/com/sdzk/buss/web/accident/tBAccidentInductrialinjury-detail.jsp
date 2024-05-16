<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>工伤等级</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
     <script type="text/javascript" src="plug-in/minicolors/jquery.minicolors.min.js"></script>
     <link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
  <script type="text/javascript">
  //编写自定义JS代码
  $(function(){
      $("#color").minicolors({
          control: $(this).attr('data-control') || 'hue',
          defaultValue: $(this).attr('data-defaultValue') || '',
          //	inline: $(this).attr('data-inline') === 'true',
          letterCase:'lowercase',
          //	opacity: $(this).attr('data-opacity'),
          position:'bottom left',
          change: function(hex, opacity) {
              var log;
              try {
                  log = hex ? hex : 'transparent';
                  if( opacity ) log += ', ' + opacity;
              } catch(e) {}
          },
          theme: 'default'
      });
  });
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBAccidentInductrialinjuryController.do?doUpdate" tiptype="3">
					<input id="id" name="id" type="hidden" value="${tBAccidentInductrialinjuryPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								工伤等级:
							</label>
						</td>
						<td class="value">
                            <label class="Validform_label">${tBAccidentInductrialinjuryPage.inductrialinjurylevel}</label>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">工伤等级</label>
						</td>
                    </tr>
            <tr>
						<td align="right">
							<label class="Validform_label">
								认定标准:
							</label>
						</td>
						<td class="value">
                            <textarea id="standard" name="standard" style="width: 440px;">${tBAccidentInductrialinjuryPage.standard}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">认定标准</label>
						</td>
					</tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">
                        <font color="red">*</font>显示排序:
                    </label>
                </td>
                <td class="value">
                    <label class="Validform_label">${tBAccidentInductrialinjuryPage.sortindex}</label>
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">显示排序</label>
                </td>
            </tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								显示颜色:
							</label>
						</td>
						<td class="value">
                            <div class="minicolors minicolors-theme-default minicolors-position-bottom minicolors-position-left">
                                <input class="minicolors-input" readOnly="true" style="border:0px;width: 124px; padding-left: 26px;" type="text" value="${tBAccidentInductrialinjuryPage.color}">
                                <span class="minicolors-swatch" style="top:0px;">
                                    <span class="minicolors-swatch-color" style="background-color: ${tBAccidentInductrialinjuryPage.color};">
                                    </span>
                                </span>
                            </div>
						</td>
                    </tr>

			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/accident/js/tBAccidentInductrialinjury.js"></script>