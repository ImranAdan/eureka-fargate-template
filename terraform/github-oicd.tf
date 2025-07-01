resource "aws_iam_role" "github_oidc_ecr_push" {
  name = "github-oidc-ecr-push"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect = "Allow",
        Principal = {
          Federated = "arn:aws:iam::${data.aws_caller_identity.current.account_id}:oidc-provider/token.actions.githubusercontent.com"
        },
        Action = "sts:AssumeRoleWithWebIdentity",
        Condition = {
          StringLike = {
            # Restrict to only your specific GitHub repo
            "token.actions.githubusercontent.com:sub" = "repo:ImranAdan/eureka-fargate-template:*"
          }
        }
      }
    ]
  })
}

resource "aws_iam_role_policy" "ecr_push_policy" {
  role = aws_iam_role.github_oidc_ecr_push.id

  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
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
      }
    ]
  })
}

data "aws_caller_identity" "current" {}
