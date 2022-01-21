call mvn -P release deploy:deploy-file -Durl=homesoft-framework-core-1.0.0-RELEASE-javadoc.jar \
                       -Dclassifier=javadoc \
                       -DgroupId=top.homesoft \
                       -DartifactId=homesoft-framework-core \
                       -Dversion=1.0.0-RELEASE \
                       -Dpackaging=jar \ 
					   
					   
call mvn -P release deploy:deploy-file -Dfile=C:\Users\user\.m2\repository\top\homesoft/homesoft-framework-id-generator/1.0.0-RELEASE/homesoft-framework-id-generator-1.0.0-RELEASE-javadoc.jar \
                       -Dclassifier=javadoc \
                       -DgroupId=top.homesoft \
                       -DartifactId=homesoft-framework-id-generator \
                       -Dversion=1.0.0-RELEASE \
                       -Dpackaging=jar \
                       -Durl=https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/
					   
call mvn -P release deploy:deploy-file -Dfile=homesoft-framework-log-1.0.0-RELEASE-javadoc.jar \
                       -Dclassifier=javadoc \
                       -DgroupId=top.homesoft \                     
                       -DartifactId=homesoft-framework-log \
                       -Dversion=1.0.0-RELEASE \
                       -Dpackaging=jar \
                       -Durl=https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/
					   
					   
call mvn -P release deploy:deploy-file -Dfile=homesoft-framework-datasource-1.0.0-RELEASE-javadoc.jar \
                       -Dclassifier=javadoc \
                       -DgroupId=top.homesoft \                     
                       -DartifactId=homesoft-framework-datasource \
                       -Dversion=1.0.0-RELEASE \
                       -Dpackaging=jar \
                       -Durl=https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/
					   
					   
call mvn -P release deploy:deploy-file -Dfile=homesoft-framework-oauth2-1.0.0-RELEASE-javadoc.jar \
                       -Dclassifier=javadoc \
                       -DgroupId=top.homesoft \                     
                       -DartifactId=homesoft-framework-oauth2 \
                       -Dversion=1.0.0-RELEASE \
                       -Dpackaging=jar \
                       -Durl=https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/
					   
					   
call mvn -P release deploy:deploy-file -Dfile=homesoft-framework-rabbit-1.0.0-RELEASE-javadoc.jar \
                       -Dclassifier=javadoc \
                       -DgroupId=top.homesoft \                     
                       -DartifactId=homesoft-framework-rabbit \
                       -Dversion=1.0.0-RELEASE \
                       -Dpackaging=jar \
                       -Durl=https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/
					   
					   
call mvn -P release deploy:deploy-file -Dfile=homesoft-framework-tools-1.0.0-RELEASE-javadoc.jar \
                       -Dclassifier=javadoc \
                       -DgroupId=top.homesoft \                     
                       -DartifactId=homesoft-framework-tools \
                       -Dversion=1.0.0-RELEASE \
                       -Dpackaging=jar \
                       -Durl=https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/
					   
					   
call mvn -P release deploy:deploy-file -Dfile=homesoft-framework-zipkin-1.0.0-RELEASE-javadoc.jar \
                       -Dclassifier=javadoc \
                       -DgroupId=top.homesoft \                     
                       -DartifactId=homesoft-framework-zipkin \
                       -Dversion=1.0.0-RELEASE \
                       -Dpackaging=jar \
                       -Durl=https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/
					   
					   
call mvn -P release deploy:deploy-file -Dfile=homesoft-framework-swagger-1.0.0-RELEASE-javadoc.jar \
                       -Dclassifier=javadoc \
                       -DgroupId=top.homesoft \                     
                       -DartifactId=homesoft-framework-swagger \
                       -Dversion=1.0.0-RELEASE \
                       -Dpackaging=jar \
                       -Durl=https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/
					   
					   
pause;