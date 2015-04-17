#include <stdio.h>
#include <stdlib.h>
#include <string.h>


int reverse(int a) {
	int m = 26, x;
	a %= m;
	for (x = 1; x < m; x++) {
		if ((a*x) % m == 1) return x;
	}
	return -1;
}


void codeCezar(){
	int key, c, diffrence;
	FILE *keyF, *text, *coded;

	keyF = fopen("key.txt", "r");
	fscanf(keyF, "%d", &key);
	fclose(keyF);
	//printf("key: %d \n", key);
	if (key <= 1 || key >= 26){
		printf("Zly klucz\n");
		exit(1);
	}
	text = fopen("plain.txt", "r");

	coded = fopen("crypto.txt", "w");
	while ((c = fgetc(text)) != EOF){
		//printf("*%d ",c);
		if ((c >= 97 && c <= 122) || (c >= 65 && c <= 90)){
			if (c >= 97 && c <= 122) diffrence = 97;
			if (c >= 65 && c <= 90) diffrence = 65;
			c = ((c - diffrence) + key) % 26;
			if (c<0) c += 26;
			c += diffrence;
		}
		fprintf(coded, "%c", c);
	}
	fclose(coded);
	fclose(text);

}


void decodeCezar(){
	int key, c, diffrence;
	FILE *keyF, *decoded, *coded;

	keyF = fopen("key.txt", "r");
	fscanf(keyF, "%d", &key);
	fclose(keyF);
	if (key <= 1 || key >= 26){
		printf("\nZly klucz\n");
		exit(1);
	}
	coded = fopen("crypto.txt", "r");

	decoded = fopen("decrypt.txt", "w");
	while ((c = fgetc(coded)) != EOF){
		//printf("*%d ",c);
		if ((c >= 97 && c <= 122) || (c >= 65 && c <= 90)){
			if (c >= 97 && c <= 122) diffrence = 97;
			if (c >= 65 && c <= 90) diffrence = 65;
			c = ((c - diffrence) - key) % 26;
			if (c<0) c += 26;
			c += diffrence;
		}
		fprintf(decoded, "%c", c);
	}
	fclose(decoded);
	fclose(coded);

}

void plainCezar(){
	int key = -1, c, diffrence, coded;
	FILE *text, *coding;

	text = fopen("extra.txt", "r");
	coding = fopen("crypto.txt", "r");
	while (key == -1 && (c = fgetc(text)) != EOF){
		//printf("* %d \n", c);
		coded = fgetc(coding);
		//printf(" %d \n", coded);
		if ((c >= 97 && c <= 122) || (c >= 65 && c <= 90)){
			key = coded - c;
			if (key<0) key += 26;

		}
	}
	printf("key: %d\n", key);
	if (key == -1){
		printf("Porazka, nie udalo sie obliczyc, za malo znakow w extra.txt (min. jeden znak a-zA-Z)");
		exit(1);
	}
	fclose(coding);
	fclose(text);


	coding = fopen("crypto.txt", "r");
	text = fopen("decrypt.txt", "w");

	while ((c = fgetc(coding)) != EOF){
		//printf("*%d ",c);
		if ((c >= 97 && c <= 122) || (c >= 65 && c <= 90)){
			if (c >= 97 && c <= 122) diffrence = 97;
			if (c >= 65 && c <= 90) diffrence = 65;
			c = ((c - diffrence) - key) % 26;
			if (c<0) c += 26;
			c += diffrence;
		}
		fprintf(text, "%c", c);
	}

	fclose(text);
	fclose(coding);
}

void cryptoCezar(){
	FILE *text, *coding;
	int c, diffrence, key;
	coding = fopen("crypto.txt", "r");
	text = fopen("decrypt.txt", "w");
	c = fgetc(coding);


	for (key = 1; key<26; key++){
		fseek(coding, 0, 0);
		while ((c = fgetc(coding)) != EOF){
			//printf("*%d ",c);
			if ((c >= 97 && c <= 122) || (c >= 65 && c <= 90)){
				if (c >= 97 && c <= 122) diffrence = 97;
				if (c >= 65 && c <= 90) diffrence = 65;
				c = ((c - diffrence) - key) % 26;
				if (c<0) c += 26;
				c += diffrence;
			}
			fprintf(text, "%c", c);
		}
		fprintf(text, "\n------------------\n");
	}

	fclose(text);
	fclose(coding);
}

void codeAffine(){
	int c, a, b, diffrence;
	FILE *keyF, *text, *coded;

	keyF = fopen("key.txt", "r");
	fscanf(keyF, "%d", &b);
	fscanf(keyF, "%d", &a);
	fclose(keyF);
	//printf("\n keys: (a)%d, (b)%d \n", a, b);

	text = fopen("plain.txt", "r");

	coded = fopen("crypto.txt", "w");
	while ((c = fgetc(text)) != EOF){
		//printf("*%d ",c);
		if ((c >= 97 && c <= 122) || (c >= 65 && c <= 90)){
			if (c >= 97 && c <= 122) diffrence = 97;
			if (c >= 65 && c <= 90) diffrence = 65;
			c = (a*(c - diffrence) + b) % 26;
			if (c<0) c += 26;
			c += diffrence;
		}
		fprintf(coded, "%c", c);
	}
	fclose(coded);
	fclose(text);

}

void decodeAffine(){
	int a, b, c, diffrence, x;
	FILE *keyF, *decoded, *coded;

	keyF = fopen("key.txt", "r");
	fscanf(keyF, "%d", &b);
	fscanf(keyF, "%d", &a);
	fclose(keyF);
	//printf("\n keys: (a)%d, (b)%d \n", a, b);

	coded = fopen("crypto.txt", "r");

	decoded = fopen("decrypt.txt", "w");
	x = reverse(a);
	if (x == -1){
		printf("Nie mozna obliczyc odwrotnosci podanej liczby, zly podany drugi klucz, program zakonczony porazka!\n");
		exit(1);
	}
	//printf("Odrotnosc liczby %d(mod 26) jest %d \n", a,x);
	while ((c = fgetc(coded)) != EOF){
		//printf("*%d ",c);

		if ((c >= 97 && c <= 122) || (c >= 65 && c <= 90)){
			if (c >= 97 && c <= 122) diffrence = 97;
			if (c >= 65 && c <= 90) diffrence = 65;
			c -= diffrence;
			c = (x*(c - b)) % 26;
			if (c<0) c += 26;
			c += diffrence;
		}
		fprintf(decoded, "%c", c);
	}
	fclose(decoded);
	fclose(coded);

}


int plainAffine(){
	int pom = -2, diffrence, x1, x2, y1, y2, a, b, aPom;
	FILE *text, *coding;

	text = fopen("extra.txt", "r");
	coding = fopen("crypto.txt", "r");
	while (pom == -2 && (x1 = fgetc(text)) != EOF){
		//printf("* %d \n", x1);
		y1 = fgetc(coding);
		//printf(" %d \n", y1);
		if ((x1 >= 97 && x1 <= 122) || (x1 >= 65 && x1 <= 90)){
			if (x1 >= 97 && x1 <= 122) diffrence = 97;
			if (x1 >= 65 && x1 <= 90) diffrence = 65;
			x1 -= diffrence;
			y1 -= diffrence;
			pom += 1;
		}
	}

	while (pom == -1 && (x2 = fgetc(text)) != EOF){
		//printf("* %d \n", x2);
		y2 = fgetc(coding);
		//printf(" %d \n", y2);
		if ((x2 >= 97 && x2 <= 122) || (x2 >= 65 && x2 <= 90)){
			if (x2 >= 97 && x2 <= 122) diffrence = 97;
			if (x2 >= 65 && x2 <= 90) diffrence = 65;
			x2 -= diffrence;
			y2 -= diffrence;
			if (x1 != x2) {
				aPom = (x1 - x2) % 26;
				if (aPom<0) aPom += 26;
				if (reverse(aPom) != -1) pom += 1;
			}
		}
	}
	if (pom <= -1){
		printf("Porazka, nie udalo sie obliczyc, za malo znakow w extra.txt (min. dwa rozne znaki a-zA-Z, a róznica miedzy nimi: NWD(26, roznica)==1)");
		exit(1);
	}

	a = (y1 - y2);
	if (a<0) a += 26;
	a = a % 26;

	aPom = reverse(a);
	printf("Odrotnosc liczby %d(mod 26) jest %d \n", a, aPom);
	a = (a*aPom) % 26;

	b = (y1 - (a*x1) % 26);
	if (b<0) b += 26;
	b = b % 26;
	printf("y1: %d, y2: %d, x1:%d, x2:%d\n", y1,y2,x1,x2);
	a = (((26 + y2 - y1) % 26)*reverse((26 + x2 - x1) % 26)) % 26;
	printf("a: %d, b: %d\n", a, b);

	fclose(coding);
	fclose(text);

	aPom = reverse(a);
	if (aPom == -1){
		printf("Nie mozna obliczyc odwrotnosci podanej liczby, zly podany drugi klucz, program zakonczony porazka!\n");
		exit(1);
	}
	printf("Odrotnosc liczby %d(mod 26) jest %d \n", a, aPom);

	coding = fopen("crypto.txt", "r");
	text = fopen("decrypt.txt", "w");

	while ((x1 = fgetc(coding)) != EOF){
		//printf("*%d ",y1);
		y1 = x1;
		if ((x1 >= 97 && x1 <= 122) || (x1 >= 65 && x1 <= 90)){
			if (x1 >= 97 && x1 <= 122) diffrence = 97;
			if (x1 >= 65 && x1 <= 90) diffrence = 65;
			x1 -= diffrence;
			y1 -= diffrence;
			y1 = (aPom*(x1 - b + 26)) % 26;
			if (y1<0) y1 += 26;
			y1 += diffrence;
			x1 += diffrence;
		}
		fprintf(text, "%c", y1);
	}

	fclose(text);
	fclose(coding);
}


void cryptoAffine(){
	FILE *text, *coding;
	int c, diffrence, a, b, x;
	coding = fopen("crypto.txt", "r");
	text = fopen("decrypt.txt", "w");
	c = fgetc(coding);


	for (a = 1; a<26; a++){
		x = reverse(a);
		if (x != -1){
			for (b = 1; b<26; b++){
				fseek(coding, 0, 0);
				while ((c = fgetc(coding)) != EOF){
					//printf("*%d ",c);
					if ((c >= 97 && c <= 122) || (c >= 65 && c <= 90)){
						if (c >= 97 && c <= 122) diffrence = 97;
						if (c >= 65 && c <= 90) diffrence = 65;
						c -= diffrence;
						c = (x*(c - b)) % 26;
						if (c<0) c += 26;
						c += diffrence;

					}
					fprintf(text, "%c", c);
				}
				fprintf(text, "\n------------------\n");
			}
		}
	}

	fclose(text);
	fclose(coding);
}



int main(int argc, char *argv[]){
	//FILE *plik;
	if (argc != 3){
		printf("Zła liczba argumentów (musi być 2)!\n");
		exit(1);
	}
	//printf("arg: %s \n", argv[1]);

	if (strcmp(argv[1], "-c") == 0 || strcmp(argv[1], "-C") == 0){
		printf("Wybrana opcja: szyfr Cezara");
		if (strcmp(argv[2], "-e") == 0 || strcmp(argv[2], "-E") == 0){
			printf(", szyfrowanie \n");
			codeCezar();
		}
		else{
			if (strcmp(argv[2], "-d") == 0 || strcmp(argv[2], "-D") == 0){
				printf(", odszyfrowanie \n");
				decodeCezar();
			}
			else{
				if (strcmp(argv[2], "-j") == 0 || strcmp(argv[2], "-J") == 0){
					printf(", kryptoanaliza z tekstem jawnym \n");
					plainCezar();
				}
				else{
					if (strcmp(argv[2], "-k") == 0 || strcmp(argv[2], "-K") == 0){
						printf(", kryptoanaliza wyłącznie w oparciu o kryptogram \n");
						cryptoCezar();
					}
					else printf("\n Zly drugi argument\n");
				}
			}
		}
	}
	else{
		if (strcmp(argv[1], "-a") == 0 || strcmp(argv[1], "-A") == 0){
			printf("Wybrana opcja: szyfr afiniczny");
			if (strcmp(argv[2], "-e") == 0 || strcmp(argv[2], "-E") == 0){
				printf(", szyfrowanie \n");
				codeAffine();
			}
			else{
				if (strcmp(argv[2], "-d") == 0 || strcmp(argv[2], "-D") == 0){
					printf(", odszyfrowanie \n");
					decodeAffine();
				}
				else{
					if (strcmp(argv[2], "-j") == 0 || strcmp(argv[2], "-J") == 0){
						printf(", kryptoanaliza z tekstem jawnym \n");
						plainAffine();
					}
					else{
						if (strcmp(argv[2], "-k") == 0 || strcmp(argv[2], "-K") == 0){
							printf(", kryptoanaliza wyłącznie w oparciu o kryptogram \n");
							cryptoAffine();
						}
						else printf("Zly drugi argument\n");
					}
				}
			}
		}
		else printf("\n Zly pierwszy argument\n");
	}

	return (0);
}
