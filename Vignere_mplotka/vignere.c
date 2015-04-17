#include <stdio.h>
#include <stdlib.h>

void prepareText(){
int c;
FILE *orig, *text;

orig = fopen("orig.txt", "r");

text = fopen("plain.txt", "w");
while((c=fgetc(orig)) != EOF){
	if (c>=97 && c<=122) fprintf(text, "%c", c);
}
fclose(text);
fclose(orig);

}

void code(){
int key, c, diff = 97;
FILE *keyF, *text, *coded;

keyF = fopen("key.txt", "r");
text = fopen("plain.txt", "r");
coded = fopen("crypto.txt", "w");
while((c=fgetc(text)) != EOF){
	if (  (key=fgetc(keyF)) == EOF) fseek(keyF, 0, 0);
	c = (c-diff+key)%26;
	
	if (c<0) c+=26;
	c+=diff;
	fprintf(coded, "%c", c);
	//printf("c: %c \n", c);
}
fclose(coded);
fclose(text);
fclose(keyF);

}


void decode(){
int key, c, diff =97;
FILE *keyF, *decoded, *coded;

keyF = fopen("key.txt", "r");
coded = fopen("crypto.txt", "r");
decoded = fopen("decrypto.txt", "w");
while((c=fgetc(coded)) != EOF){
	if (  (key=fgetc(keyF)) == EOF) fseek(keyF, 0, 0);
		c = (c - diff-key)%26;
		if (c<0) c+=26;
		c +=diff;
		fprintf(decoded, "%c", c);
	
}
fclose(decoded);
fclose(coded);
fclose(keyF);

}

int main(int argc, char *argv[]){
//FILE *plik;
if(argc != 2){
	printf("Zła liczba argumentów (musi być 1)!\n");
	exit(1);
}

if (strcmp(argv[1],"-p") == 0 || strcmp(argv[1],"-P") == 0){
	printf("Wybrano: przygotowanie tekstu jawnego do szyfrowania\n");		
	prepareText();
}
else{
 if (strcmp(argv[1],"-e") == 0 || strcmp(argv[1],"-E") == 0){
	printf("Wybrano: szyfrowanie \n");
	code();
 }
  else{
   if (strcmp(argv[1],"-d") == 0 || strcmp(argv[1],"-D") == 0){
	printf("Wybrano: odszyfrowanie\n");
	decode(); 	 
   }
   else{
    if (strcmp(argv[1],"-k") == 0 || strcmp(argv[1],"-K") == 0){
	printf("Wybrano: kryptoanaliza wyłącznie w oparciu o kryptogram (niezaimplementowane)\n");
	//cryptoAffine();
    }
    else printf("Zly argument\n"); 		   	
   }
}
}



return (0);
}
