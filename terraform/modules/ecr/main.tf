resource "aws_ecr_repository" "eureka" {
  name = "eureka-server"
  image_scanning_configuration {
    scan_on_push = true
  }
}

resource "aws_ecr_repository" "configserver" {
  name = "config-server"
  image_scanning_configuration {
    scan_on_push = true
  }
}

resource "aws_ecr_repository" "battle" {
  name = "battle-service"
  image_scanning_configuration {
    scan_on_push = true
  }
}
