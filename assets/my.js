function showit(text) {
    alert(text);
}    
var index=1;
var iAmClicked=function(event){
	if(confirm("Add this content in your notebook.")){
       event.target.style.backgroundColor="yellow";       
        event.target.dataIndex=index++;
        event.target.dataMarked=true;       
       }
    event.stopPropagation();
};

var init=function(){  
    var a=document.getElementsByTagName("a");
    for(i=0;i<a.length;i++){
        a[i].href="javascript:void(0)";
    }
    var x=document.getElementsByTagName("p");
    for(i=0;i<x.length;i++){
        x[i].onclick=iAmClicked;
    }
    var imgTagElement=document.getElementsByTagName("img");
    var imgTagLength=document.getElementsByTagName("img").length;
    for (var j=0;j<imgTagLength;j++) {
        imgTagElement[j].onclick=iAmClicked;
    }
 }; 
var getKeys=function(obj){
    var keys = [];
    for (var key in obj) {
        keys.push(key);
    }
    return keys;
 };  
var getData=function(){
    var all = [];
    var x=document.getElementsByTagName("p");
    for(i=0;i<x.length;i++){
        if(x[i].style.backgroundColor=="yellow"){
            all[x[i].dataIndex]=x[i].innerText;
        }
    }
    var img=document.getElementsByTagName("img");
    for(j=0;j<img.length;j++){
       if(img[j].dataMarked){
            all[img[j].dataIndex]=img[j].src;
        }
    }
    var arrAll=[];
    var keys=getKeys(all);
    for(var i=0;i<keys.length;i++){
        arrAll.push(all[keys[i]]);
    }
    return arrAll.join(":::");
};
var AllKeys=function(obj){
	var keys = [getData()];
	 return keys;
};