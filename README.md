# devops-bootcamp-project-1
Demo project for Module 5 - Cloud &amp; IaaS

Steps taken to complete demo:

1. clone repo git@gitlab.com:nanuchi/java-maven-app.git locally

2. create own repo github.com:JulienAvezou/devops-bootcamp-project-1.git and push code there

3. build jar file using following maven cmd -> mvn install
<img width="633" alt="Capture d’écran 2022-10-12 à 00 18 42" src="https://user-images.githubusercontent.com/62488871/195208562-3846604d-66d2-4b2b-8b8c-03f949e740d5.png">

4. create server/droplet on Digitalocean
<img width="1187" alt="Capture d’écran 2022-10-11 à 23 53 05" src="https://user-images.githubusercontent.com/62488871/195205243-99f33d58-efce-40d2-99f2-5dbaf4f5270f.png">

5. add SSH public key from local user to newly created server to allow for SSH login

6. add Firewall rules to server
open port 22 for SSH login, restrict to local computer IP address

7. login to remote server using SSH -> ssh root@164.90.167.207

8. create new user for the app (best practice) -> adduser ju

9. add user to sudo group -> usermod -aG sudo ju

10. switch to new user -> su - ju

11. add file .ssh/authorized_keys in home dir of ju on remote server, and paste public key of my local user, now I can ssh into remote user ju on remote server 

12. copy jar file over to remote server -> scp target/java-maven-app-1.1.0-SNAPSHOT.jar ju@164.90.167.207:/root

13. run jar file on remote server -> java -jar java-maven-app-1.1.0-SNAPSHOT.jar

14. check on which port app is running, lists servers that have active connections -> netstat -lpnt (in our case it is port 8080)

15. add custom rule on Digitalocean Firewall rules for the droplet to allow Inbound request on port 8080

16. check in the browser that the app has started correctly
<img width="1193" alt="Capture d’écran 2022-10-12 à 00 12 27" src="https://user-images.githubusercontent.com/62488871/195207763-26ff1b06-7c5b-48e3-b56e-2d53033e9d30.png">
<img width="524" alt="Capture d’écran 2022-10-12 à 00 14 06" src="https://user-images.githubusercontent.com/62488871/195207972-2aced689-599f-43f8-b4d1-efeb52acddb7.png">

17. exit process and exit remote server
