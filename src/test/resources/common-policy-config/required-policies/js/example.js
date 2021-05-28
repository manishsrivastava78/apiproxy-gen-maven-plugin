function sum(num1,num2){
	return num1+num2;
}
if(typeof exports !== 'undefined'){
	exports.sum = sum;
}else{
	sum(2,2);
}