# 📺 TvPourTous_Control - Application compagnon de **TvPourTous** 🎮📱

## 📌 Présentation
**TvPourTous_Control** est une application **Android** permettant de **contrôler à distance** l'application de bureau **TvPourTous**.  
Grâce à cette application, vous pouvez :
- 📡 **Scanner automatiquement** le réseau pour détecter les serveurs TvPourTous disponibles.
- 📜 **Récupérer la liste des chaînes IPTV** disponibles sur votre serveur TvPourTous.
- 🎮 **Changer la chaîne à distance** directement depuis votre téléphone.
- 🔍 **Filtrer les chaînes dynamiquement** en tapant dans la barre de recherche.

> **🔗 Téléchargement du logiciel TvPourTous (PC) :**  
> 👉 [Télécharger TvPourTous pour Windows](https://github.com/rodrigueantunes/TvPourTous/releases/download/TvPourTous_0.1.3/Tv.Pour.Tous.zip)

---

## ⚙️ **Installation & Configuration**
### 🔹 **Installation sur Windows (Serveur)**
1. **Téléchargez TvPourTous** depuis [ce lien](https://github.com/rodrigueantunes/TvPourTous/releases/download/TvPourTous_0.1.3/Tv.Pour.Tous.zip).
2. **Extrayez l'archive** et lancez `TvPourTous.exe`.
3. L'application démarre et **active automatiquement un serveur API sur le port 5000**.

### 📱 **Installation sur Android**
1. **Téléchargez et installez l'APK de TvPourTous_Control** (à venir sur le Play Store ou en APK via GitHub).
2. **Ouvrez l'application** et appuyez sur "Scanner le réseau".
3. **Sélectionnez votre serveur** détecté (ex: `192.168.1.10:5000`).
4. **Parcourez les chaînes disponibles** et **cliquez sur une chaîne pour la lire sur TvPourTous (PC)**.

---

## 🚀 **Fonctionnalités principales**
### 🔹 **Sur TvPourTous (PC)**
✅ Lecture de flux IPTV directement depuis l'application (pas besoin de VLC).  
✅ **Interface moderne & rapide** avec support de **FFmpeg** et **FFMediaElement**.  
✅ **Gestion complète des sources M3U** (ajout, suppression, modification).  
✅ **Mode plein écran ultra fluide** avec changement de chaîne sans quitter le mode.  
✅ **API HTTP intégrée** pour un contrôle distant.

### 📱 **Sur TvPourTous_Control (Android)**
✅ **Scan automatique** du réseau pour détecter le serveur TvPourTous.  
✅ **Liste des chaînes IPTV synchronisée** avec le serveur.  
✅ **Changement de chaîne à distance** en un clic.  
✅ **Filtrage intelligent des chaînes** en tapant du texte.  
✅ **Interface rapide & légère**, sans pub ni tracking.  

---

## 📡 **Comment fonctionne le contrôle à distance ?**
1. **Le logiciel TvPourTous (PC)** démarre un **serveur API HTTP sur le port 5000**.
2. **L'application Android TvPourTous_Control** détecte les serveurs actifs en scannant les IP du réseau local.
3. L'application Android récupère la **liste des chaînes disponibles** via l'API (`/api/channels`).
4. Lorsqu'une chaîne est sélectionnée, l'application Android envoie une **requête HTTP** pour changer la chaîne (`/api/channelcontrol/change`).
5. TvPourTous (PC) met immédiatement à jour le flux en conséquence.

---

## 🛠 **Dépannage & FAQ**
### ❓ **Pourquoi l'application Android ne trouve pas mon PC ?**
✅ Vérifiez que votre téléphone est **connecté au même réseau WiFi** que votre PC.  
✅ **Désactivez temporairement votre pare-feu Windows** ou ajoutez une **règle autorisant le port 5000**.  
✅ Testez manuellement en ouvrant `http://<IP_DE_VOTRE_PC>:5000/api/channels` dans un navigateur.

### ❓ **Le changement de chaîne ne fonctionne pas en mode plein écran**
✅ Une mise à jour a corrigé ce problème. Assurez-vous d'utiliser **TvPourTous 0.1.3 ou plus récent**.

### ❓ **Comment ajouter de nouvelles sources M3U ?**
✅ Allez dans `Gérer les sources` sur TvPourTous (PC) et ajoutez un lien M3U valide.  
✅ Redémarrez l'application ou rechargez les chaînes pour voir les modifications.

---

## 🏗️ **Roadmap (Fonctionnalités à venir)**
✅ **Support Android TV** (interface optimisée pour la télécommande).  
✅ **Ajout des favoris & historique des chaînes** sur l'application Android.  
✅ **Synchronisation des paramètres** entre plusieurs appareils.  
✅ **Mode PiP (Picture-in-Picture)** sur Android.

---

## 🤝 **Contributions & Remerciements**
Les contributions sont les bienvenues ! N'hésitez pas à proposer des améliorations via des **Issues** ou des **Pull Requests**.  

📌 **Développeur** : [Rodrigue Antunes](https://github.com/rodrigueantunes)  
📌 **Dépôt officiel** : [GitHub TvPourTous](https://github.com/rodrigueantunes/TvPourTous)  

---

## 📜 **Licence**
Ce projet est sous licence **MIT**, ce qui signifie que vous êtes libre de le modifier et de le redistribuer sous certaines conditions.  

📄 Consultez le fichier **LICENSE** pour plus d’informations.  

🚀 **Profitez de votre IPTV avec TvPourTous & TvPourTous_Control !** 📡🎮📱
