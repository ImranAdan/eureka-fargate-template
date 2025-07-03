resource "aws_iam_openid_connect_provider" "github" {
  url = "https://token.actions.githubusercontent.com"

  client_id_list = [
    "sts.amazonaws.com"
  ]

  thumbprint_list = [
    "6938fd4d98bab03faadb97b34396831e3780aea1" # GitHub's published thumbprint
  ]
}

data "aws_caller_identity" "current" {}

# --- ECR Push Role ---

resource "aws_iam_role" "github_oidc_ecr_push" {
  name = "github-oidc-ecr-push"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [{
      Effect = "Allow",
      Principal = {
        Federated = aws_iam_openid_connect_provider.github.arn
      },
      Action = "sts:AssumeRoleWithWebIdentity",
      Condition = {
        StringLike = {
          "token.actions.githubusercontent.com:sub" = "repo:ImranAdan/eureka-fargate-template:*"
        }
      }
    }]
  })
}

resource "aws_iam_role_policy" "ecr_push_policy" {
  role = aws_iam_role.github_oidc_ecr_push.id

  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [{
      Effect = "Allow",
      Action = [
        "ecr:GetAuthorizationToken",
        "ecr:BatchCheckLayerAvailability",
        "ecr:CompleteLayerUpload",
        "ecr:InitiateLayerUpload",
        "ecr:PutImage",
        "ecr:UploadLayerPart",
        "ecr:BatchGetImage",
      ],
      Resource = "*"
    }]
  })
}

# --- Custom Least-Privilege Policy for Terraform ---

resource "aws_iam_policy" "terraform_least_privilege" {
  name        = "terraform-least-privilege"
  description = "Least privilege policy for Terraform plan/apply"

  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [{
      Effect = "Allow",
      Action = [
        "ec2:*",
        "ecs:*",
        "elasticloadbalancing:*",
        "iam:GetRole",
        "iam:ListRoles",
        "iam:PassRole",
        "logs:*",
        "ecr:GetAuthorizationToken",
        "ecr:BatchGetImage",
        "ecr:GetDownloadUrlForLayer"
      ],
      Resource = "*"
    }]
  })
}

# --- Terraform Plan/Apply Role ---

resource "aws_iam_role" "github_oidc_terraform" {
  name = "github-oidc-terraform"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [{
      Effect = "Allow",
      Principal = {
        Federated = aws_iam_openid_connect_provider.github.arn
      },
      Action = "sts:AssumeRoleWithWebIdentity",
      Condition = {
        StringLike = {
          "token.actions.githubusercontent.com:sub" = "repo:ImranAdan/eureka-fargate-template:*"
        }
      }
    }]
  })
}

resource "aws_iam_role_policy_attachment" "terraform_limited_policy" {
  role       = aws_iam_role.github_oidc_terraform.name
  policy_arn = aws_iam_policy.terraform_least_privilege.arn
}
