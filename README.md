# Ambari Webpage Embedder View [ ![Download](https://api.bintray.com/packages/big-data/rpm/ambari-webpage-embedder-view/images/download.svg) ](https://bintray.com/big-data/rpm/ambari-webpage-embedder-view/_latestVersion)
<img align="right" src="https://github.com/tzolov/ambari-webpage-embedder-view/blob/master/doc/images/zeppelin-view.png" alt="zeppelin-view" width="350"></img>
This plugin helps to display external web pages content within Ambari. Implemented as an [Ambari View](https://cwiki.apache.org/confluence/display/AMBARI/Views) plugin it uses [iframe](http://www.w3schools.com/html/html_iframe.asp)s to inline the webpage content as a view. Once installed the `Ambari Webpage Embedder View` can be used to embed multiple webpages. Just create a new view instance for every new webpage. You can use either the Ambari [Create View Instance](http://docs.hortonworks.com/HDPDocuments/Ambari-2.0.1.0/bk_Ambari_Admin_Guide/content/_creating_view_instances.html) wizard or the View REST API ([see below](https://github.com/tzolov/ambari-webpage-embedder-view#create-instance-with-ambari-rest-api)). The embedded web page appear in Ambari Views section.

Compatible with `Ambari` `1.7`, `2.0.x` and `2.1.x` and Stacks: `PHD3.0`, `HDP2.2x` and `HDP2.3`.

[<img align="left" src="http://img.youtube.com/vi/BGPQjHYSVQk/3.jpg" alt="zeppelin-view" hspace="15" width="70"></img>](https://www.youtube.com/watch?v=BGPQjHYSVQk)
The [Embed Webpage Video](https://www.youtube.com/watch?v=BGPQjHYSVQk) illustrates the capabilities of the plugin. It shows how to install it from an YUM repository and how to embed web application pages like Zeppelin, Spring XD Admin and HDFS explorer.


## Quick Start
For RedHat/CentOS platforms install the `ambari-webpage-embedder-view`rpm from the [Big-Data](https://bintray.com/big-data/rpm) YUM repository and restart Ambari server.
```
sudo wget https://bintray.com/big-data/rpm/rpm -O /etc/yum.repos.d/bintray-big-data-rpm.repo
sudo yum -y install ambari-webpage-embedder-view
sudo /etc/init.d/ambari-server restart
```
After the installation verify that the `WEBPAGE_EMBEDDER` plugin is listed in the `Admi/Manage Ambari/Views`. Then follow the instructions to embed a sample web page:

<img align="right" src="https://github.com/tzolov/ambari-webpage-embedder-view/blob/master/doc/images/create-zeppelin-view-instance.png" alt="create-zeppelin-view-instance" width="300"></img>

1. Open the `Admin/Manage Ambari` configuration panel. 
2. Click on `Views`, select `WEBPAGE_EMBEDDER` and press `+Create Instance`
3. Fill in the details. For example `Instance Name: ZEPPELIN`, `Display Name: Zeppelin`, `Description: My zeppelin view`.
4. Set the webpage url in the `embedded.webpage.url` property. Use the full URL format: `http(s)://<web-page>:<port>`. To embed a Zeppelin service running on ambari.localdomain:9995, set the property to `http://ambari.localdomain:9995`
5. Press `Save`. 
6. Follow the `go to instnace` linke (or use the `Views` panel) to open the new view instance. 

## Build and Install
Instructions below explain how to build the plugin from scratch and install it (manually) in Ambari. You can choose between Jar, Rpm and Depbian installation packages.
#### Jars
```
./gradlew clean build
```
builds a ambari-webpage-embedder-view jar in the `build/libs` folder. Copy the jar into Ambari's `var/lib/ambari-server/resources/views` folder and restart the Ambari Server.

#### Rpm
```
./gradlew clean buildRpm
```
produced rpm will appear in `build/distributions` folder. Copy the rpm to Ambari and run:
```
sudo yum -y install ./ambari-webpage-embedder-view-0.0.4-1.noarch.rpm
sudo /etc/init.d/ambari-server restart
```
#### Deb
```
./gradlew clean buildDeb
```
produced deb package will appear in `build/distributions` folder. Copy the Deb package to an Ambari folder and run:
```
dpkg -i ambari-webpage-embedder-view-0.0.4-1.noarch.rpm
sudo /etc/init.d/ambari-server restart
```
## Embed New Web Page
To embed a new page, create new `WEBPAGE_EMBEDDER` instance and set the URL of the desired Web application. For example to embed the Apache Zeppelin application running on host: `ambari.localdomain` and port `9995` one need to create a new `WEBPAGE_EMBEDDER` instance and set the `embedded.webpage.url` to `http://ambari.localdomain:9995`.
#### Create instance with Ambari Wizard. 
Follow the [Quick Start](https://github.com/tzolov/ambari-webpage-embedder-view#quick-start) instructions above to create new WEBPAGE_EMBEDDER instance.

#### Create instance with Ambari REST API

Use an HTTP POST to create a new WEBPAGE_EMBEDDER instance. 
```json
POST /api/v1/views/WEBPAGE_EMBEDDER/versions/<version>/instances/<Instance_Name> 
[ {
 "ViewInstanceInfo": { 
  "instance_name" : "Instance_Name",
	 "label" : "Instaance label",
	 "visible" : true,
	 "icon_path" : "Instance icon path",
	 "icon64_path" : "Instance icon path",
	 "description" : "Instance Description",
	 "properties":{
	    "embedded.webpage.url":"<WEB APPLICATION URL>"
	 }
	} 
}]
```
The `version` path element corresponds to the Version of the ambari-webpage-embedder-view (WEBPAGE_EMBEDDER) installed. Use  `/api/v1/views/WEBPAGE_EMBEDDER/versions/<version>/instances/` to check the list of the installed instances.
Note that `Instance_Name` in the path element must match the `instance_name` field. 
The `embedded.webpage.url` property should point to the Web application URL being embedded. It includes the protocol prefix and the port number. 
For example to create Zeppelin view instance run:
```
curl -u admin:admin -i -H 'X-Requested-By: ambari' -X POST -d '[{"ViewInstanceInfo": { "instance_name":"MY_ZEPPELIN","label":"Zeppelin", "description":"Ambari Zeppelin View","properties" : {"embedded.webpage.url": "http://ambari.localdomain:9995"}}}]' http://ambari.localdomain:8080/api/v1/views/WEBPAGE_EMBEDDER/versions/0.0.3/instances/MY_ZEPPELIN
```

To remove an instance with the REST API run:
```
DELETE /api/v1/views/WEBPAGE_EMBEDDER/versions/<version>/instances/<Instance_Name> 
```
For example: to delete the Zeppelin instance run:
```
curl --user admin:admin  -H 'X-Requested-By:mycompany' -X DELETE http://ambari.localdomain:8080/api/v1/views/WEBPAGE_EMBEDDER/versions/0.0.3/instances/MY_ZEPPELIN
```

## Public YUM Repository
The `ambari-webpage-embedder-view` RPMs are distributed through the [Big-Data](https://bintray.com/big-data/rpm) public YUM repository.  

#### Install the plugin from the Big-Data YUM repository
Add the Big-Data to your YUM repositories, use `yum` to install `ambari-webpage-embedder-view` and restart Ambari.
```
sudo wget https://bintray.com/big-data/rpm/rpm -O /etc/yum.repos.d/bintray-big-data-rpm.repo
sudo yum -y install ambari-webpage-embedder-view
sudo /etc/init.d/ambari-server restart
```
#### Upload new plugin RPMs to the Big-Data YUM repository
```
./gradlew clean buildRpm bintrayUpload
```
Make sure that the `BINTRAY_BIGDATA_USER` and `BINTRAY_BIGDATA_KEY` variables are set.
Finally go to the Bintray [ambari-webpage-embedder-view](https://bintray.com/big-data/rpm/ambari-webpage-embedder-view/view) project and publish the newly uploaded RPMs.

## Related work
The [ambari-iframe-view](https://github.com/hortonworks-gallery/ambari-iframe-view) work by @aliabajwa applies the same idea but the implementation is static. To add a new new web page one has to clone the project, modify the code, build and install it as a separate ambari view plugin. Instead the `ambari-webpage-embedded-view` installs the view plugin once (preferably through the YUM repository) and then allows you to create as many instances as is the number of web pages you need to embed. 
