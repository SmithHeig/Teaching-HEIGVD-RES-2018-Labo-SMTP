# RES - Labo SMTP: RobotMail
## Présentation
Application permettant de générer des pranks qui envoie des emails forgé en spam.

Ce projet contient un serveur MockMock qui permet de simuler un serveur SMTP pour éviter de spammer des serveur "réel".

## Configuration
### MailRobot

Pour lancé l'application, il faut configurer les fichiers suivant du dossier ./config/ :

- **config.properties**
  - SMTPServerAdress=localhost
  - SMTPServerPort=2525
  - nbGroups=5
  - witnessesToCC=james.smith@heig-vd.ch

- **message.utf8**
  Subject: your subject

  your message

  - **==** est le séparator entre les messages

- **victims.utf8**

  - Contient les email (ligne par ligne) des victims du spam

##### Note

Le dossier config est obligatoire pour lancer l'application et doit être situé à la racine de l'exécution de l'application.

### Propriétés

Le *MailRobot* va générer une "Prank" qui et un mail par groupe qui sera généré automatiquement et aléatoirement grâce au fichier *messsage.utf8* et *victimes.utf8*.

### MockMock

## Build

**Les deux prochaines étapes sont dans le cas ou vous faite un nouveau pull du serveur MockMock**

- Aller dans /MockMock/MockMock/

- Exécuter en ligne de commande:

  ```bash
  mvn clean install
  ```

- Aller dans target et le copier dans ./docker-server/src/

## Run avec Docker ligne de commande

- Lancer les commandes suivante depuis la racine du projet:

  ```bash
  docker build -t labo3-server-smtp ./docker-server
  docker run -p 2525:2525 -p 8282:8282 labo3-server-smtp
  ```

- Le *2525:2525* represente le port configuré pour votre serveur

- Le *8282:8282* est pour accéder à l'interface web du MockMock

## Credits

Ce projet fut réalisé dans le cadre du cours RES 2018 à l'HEIG-VD en *pair programming* par Jérémie Chatillon et James Smith. 