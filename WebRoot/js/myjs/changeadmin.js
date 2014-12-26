	function dopromotion(){
			var promotionReson=document.getElementById("promotionReson").value;
			var recommenduserId=document.getElementById("recommenduserId").value;
			if(promotionReson.length>=8){
					if(confirm('你确认提交么？')){
							$.ajax({
									type:"POST",
									data:"promotionReason="+promotionReson+"&recommenduserId="+recommenduserId,
									dataType:"JSON",
									url:"producePromotion.action",
									success:function(result){
											if(result.status==0){
													alert(result.msg);
													return false;
												}else{
														alert("succeed to initiate");
														location.reload();
														return true;
													}
										}
								});
						}
				}else{	
				alert("你输入的原因过短");
				return false;				
		}
		}