terraform {
    backend "s3" {
        bucket = "aimmo-mlops-terraform-backend"
        dynamodb_table = "terraform-backend-locks"
        encrypt = true
        key = "tutorial/shelley/terraform.tfstate"
        region = "us-east-1"
    }
}