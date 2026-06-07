job "book-store" {
  datacenters = ["dc1"]
  group "servers" {

    count = 4

    network{
      port "http" {}
    }

    task "app-customers" {
      driver = "java"
      config{
        jar_path  = "C:/prog-dsitribuida2026/app-customers-0.0.1-SNAPSHOT.jar"
        jvm_options = ["-Xmx1024m", "-Xms128m"]
      }

      env{
        SERVER_PORT = "${NOMAD_PORT_http}"
      }

      service{
        provider = "nomad"
        name = "app-customers"
        port = "http"
      }
    }
  }
}