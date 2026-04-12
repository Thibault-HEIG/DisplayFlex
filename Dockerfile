# The foundation: Starts the build process using an official image containing the 
# Java 21 runtime and development kit (JDK). This ensures your environment has all the Java tools needed.
FROM eclipse-temurin:21

# Creates a directory named '/app' inside the container and sets it as the default 
# location for all subsequent commands. This isolates your code and keeps the container's root directory clean.
WORKDIR /app

# Copies everything from your MacBook's current directory (first '.') into 
# the container's current working directory ('/app', the second '.').
COPY . .

# Executes a shell command during the image building process. 
# Here, it compiles your Java source files, links any dependencies in the 'lib/' folder, 
# and places the compiled output into an 'out' directory. Once this finishes, the resulting state is saved into the image.
RUN javac -cp "lib/*" -d out src/main/java/**/*.java

# Defines the default process that executes when the container starts running.
# Note the difference: 'RUN' is for compiling/building the image. 'CMD' is for launching the actual application (the JVM).
CMD ["java", "-cp", "out:lib/*", "main.java.server.AppServer"]