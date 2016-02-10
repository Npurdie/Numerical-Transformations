#include<stdio.h>
#include<math.h>


int main()
{
    int i=1;
    int foo;
    char hexadecimalNumber[100];
    char[100];

    printf("Please enter a floating point or IEEE number :  ");
    scanf("%d",&decimal);               //scans decimal number (input)

    if ((sizeof(int_array)/sizeof(int)) > )
    {
      /* code */
    }

    while(decimal!=0)                   //method learnt in class for hexadecimal to decimal conversion
    {
         foo = decimal % 16;

      if(foo < 10)
           foo =foo + 48;               // to # 1-10
      else
         foo = foo + 55;                // to A-E

      hexadecimalNumber[i++]= foo;
      decimal = decimal / 16;
  	}
    printf("hexadecimal number --> ");

    for(int j = i -1 ;j> 0;j--)
    {
      printf("%c",hexadecimalNumber[j]);
    }
  	printf("\n");
    return 0;
}