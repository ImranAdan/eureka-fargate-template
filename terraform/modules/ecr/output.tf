output "eureka_repository_url" {
  value = aws_ecr_repository.eureka.repository_url
}

output "configserver_repository_url" {
  value = aws_ecr_repository.configserver.repository_url
}

output "battle_repository_url" {
  value = aws_ecr_repository.battle.repository_url
}
