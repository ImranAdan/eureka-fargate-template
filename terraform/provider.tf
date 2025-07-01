terraform {
  cloud {
    organization = "imran-adan-org"

    workspaces {
      name = "eureka-frgate-template"
    }
  }
}

provider "aws" {
  region = "eu-west-2"
}