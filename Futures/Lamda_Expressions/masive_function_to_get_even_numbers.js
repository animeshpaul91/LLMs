function massive_function_to_get_even_numbers(){
const array1 = [1,2,3,4,5,6,7,8];
const array2 = [];
  for (i=0;i<8;i++){
    if (array1[i]%2==0)
    	array2[i] = array1[i];
  };
  console.log(array2);
}
massive_function_to_get_even_numbers();