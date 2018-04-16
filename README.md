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

### Propriétés

Le *MailRobot* va générer une "Prank" qui et un mail par groupe qui sera généré automatiquement et aléatoirement grâce au fichier *messsage.utf8* et *victimes.utf8*.

### MockMock

## Install

- Aller dans /MockMock/MockMock/

- Exécuter en ligne de commande:

  ```bash
  mvn clean install
  ```

#### Run

- Aller dans le dossier /MockMock/MockMock/target/


- Lancer le *jar*:

  ```bash
  java -jar MockMock-1.4.0.one-jar -p 2525
  ```

  Ou le *-p* permet de spécifier le port sur lequel le serveur va écouter. Dois matcher avec le port mis dans *config.properties*


## Credits

Ce projet fut réalisé dans le cadre du cours RES 2018 à l'HEIG-VD en *pair programming* par Jérémie Chatillon et James Smith. 