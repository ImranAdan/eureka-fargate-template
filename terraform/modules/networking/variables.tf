variable "vpc_cidr" {
  description = "CIDR block for the VPC"
  type        = string
  default     = "10.0.0.0/16"
}

variable "availability_zones" {
  description = "List of availability zones to deploy subnets"
  type        = list(string)
  default     = ["eu-west-2a", "eu-west-2b"]
}

variable "public_subnet_cidrs" {
  description = "CIDRs for the public subnets"
  type        = list(string)
  default     = ["10.0.1.0/24", "10.0.2.0/24"]
}

variable "tags" {
  description = "Common tags to apply to resources"
  type        = map(string)
  default     = {}
}

variable "private_subnet_cidrs" {
  description = "CIDRs for the private subnets"
  type        = list(string)
  default     = ["10.0.11.0/24", "10.0.12.0/24"]
}
