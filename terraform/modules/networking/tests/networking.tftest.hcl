# This file is a test configuration for a Terraform networking module. 
#It defines a test that applies the module and checks various outputs to ensure the networking setup is correct.
# The test checks for the presence of a VPC, public and private subnets, an Internet Gateway, a NAT Gateway, 
#and route tables. It also verifies that the number of availability zones matches expectations.

run "test_networking_module" {
  command = apply

  module {
    source = "../"
  }

  variables {
    vpc_cidr = "10.0.0.0/16"
    availability_zones = ["eu-west-2a", "eu-west-2b"]
    public_subnet_cidrs = ["10.0.1.0/24", "10.0.2.0/24"]
    private_subnet_cidrs = ["10.0.11.0/24", "10.0.12.0/24"]
    tags = {
      Environment = "test"
    }
  }

  assert {
    condition = output.vpc_id != ""
    error_message = "VPC ID should not be empty"
  }

  assert {
    condition = length(output.public_subnet_ids) == 2
    error_message = "Expected exactly 2 public subnets"
  }

  assert {
    condition = length(output.private_subnet_ids) == 2
    error_message = "Expected exactly 2 private subnets"
  }

  assert {
    condition = length(var.availability_zones) == 2
    error_message = "Expected exactly 2 availability zones"
  }

  assert {
    condition = output.internet_gateway_id != ""
    error_message = "Internet Gateway ID should not be empty"
  }

  assert {
    condition = output.nat_gateway_id != ""
    error_message = "NAT Gateway ID should not be empty"
  }

  assert {
    condition = output.public_route_table_id != ""
    error_message = "Public route table ID should not be empty"
  }

  assert {
    condition = output.private_route_table_id != ""
    error_message = "Private route table ID should not be empty"
  }
}