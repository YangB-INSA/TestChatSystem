#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>


int main(int argc, char *argv[])
{
    FILE* fichier = NULL;
    char chemin[100];
	char nom[20];
	int nombre; 
 
    fichier = fopen("capteurs.txt", "r"); /* on ouvre le fichier capteurs.txt
											qui se trouve dans le meme dossier */
 	
	/* on suppose que le fichier a le format décrit précedemment */
	
    if (fichier != NULL)
    {
        while(fscanf(fichier, "%s %s %d", chemin, nom, &nombre)==3){
			printf("%s\n", *chemin);
			printf("%s\n", *nom);
			printf("%s\n", nombre);
		}
 
        fclose(fichier);
    }
 
    return 0;
}

