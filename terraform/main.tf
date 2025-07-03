module "ecr" {
  source = "./modules/ecr"
}

module "networking" {
  source              = "./modules/networking"
  vpc_cidr            = "10.0.0.0/16"
  availability_zones  = ["eu-west-2a", "eu-west-2b"]
  public_subnet_cidrs = ["10.0.1.0/24", "10.0.2.0/24"]
  private_subnet_cidrs = ["10.0.11.0/24", "10.0.12.0/24"]
  tags = {
    Project = "eureka-fargate-template"
  }
}
