#!/bin/bash
set -e

GROUP_ID="com.orbyte"
ARTIFACT_ID="orbyte-parent"
VERSION="1.0.0-SNAPSHOT"

echo "Generating parent pom.xml..."

cat > pom.xml <<EOF
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         http://maven.apache.org/maven-v4_0_0.xsd">

  <modelVersion>4.0.0</modelVersion>

  <groupId>com.orbyte</groupId>
  <artifactId>payments</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>pom</packaging>
  <modules>
EOF

for dir in */ ; do
    if [ -f "${dir}pom.xml" ]; then
        module=$(basename "$dir")
        echo "        <module>${module}</module>" >> pom.xml
    fi
done

cat >> pom.xml <<EOF
    </modules>

</project>
EOF

echo "Parent pom.xml generated successfully."
echo "Detected modules:"
grep "<module>" pom.xml