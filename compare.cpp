#include<cstdlib>
#include<cstdio>
#include<ctime>
using namespace std;
int main(){
	srand(time(0));
	for(int i=1;i<=10000;i++){
		system("makedata.exe");
		double st = clock();
		system("sol.exe");
		double ed = clock();
		
		double st1 = clock();
		system("bf.exe");
		double ed1 = clock();
		if(system("fc data.out.txt out.txt")){
			printf("Wrong answer at %d");
			return 0;
		}
		else{
			printf("Accepted test#%d time:%.1lfms  bftime%.1lfms\n",i,ed-st,ed1-st1);
		}
	}
	return 0;
}
