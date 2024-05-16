<script>
  var  selecter = $(".J_iframe:visible", window.parent.document);
  selecter.attr("src", "<%=request.getContextPath()%>/webpage/common/500.htm");
</script>