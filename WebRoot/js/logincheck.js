/**********************************/
/***	��¼������֤   ***********/
/**********************************/

$(document).ready(function(){	
	// ����û��Ƿ������û���
	$("#txtusername").blur(function() {
		if ($("#txtusername").val() == "") {
			$("#usernameTd").html("<span style=\"color:red;\">�������û���</span>");
		}else{
			$("#usernameTd").hide();
			$("#tip").hide();
		}
	});
	// ����û��Ƿ���������
	$("#txtuserpassword").blur(function() {
		if ($("#txtuserpassword").val() == "") {
			$("#passwordTd").html("<span style=\"color:red;\">����������</span>");
		}else{
			$("#passwordTd").hide();
			$("#tip").hide();
		}
	});	
	// ��¼�ύ��֤
	$("#btnlogin").click(function(){
 		var userName = $("#txtusername").val();
 		var userPassword = $("#txtuserpassword").val();
		if(userName == ""){
			alert("�û�������Ϊ��");
		}else if(userPassword == ""){
			alert("���벻��Ϊ��");
		}
		else{									
			document.forms[0].submit();
		}
	});
});	

function keyLogin() {
	if (event.keyCode == 13) // �س����ļ�ֵΪ13
	{
		var userName = $("#txtusername").val();
		var userPassword = $("#txtuserpassword").val();
		if (userName == "") {
			alert("�û�������Ϊ��");
		} else if (userPassword == "") {
			alert("���벻��Ϊ��");
		} else {
			document.forms[0].submit();
		}
	}
}
