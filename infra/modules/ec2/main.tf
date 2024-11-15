resource "aws_instance" "jenkins" {
  ami                    = "ami-062cf18d655c0b1e8"
  instance_type          = var.settings.type
  key_name               = var.settings.key_name
  subnet_id              = var.subnet_id
  vpc_security_group_ids = var.vpc_security_group_ids

  root_block_device {
    volume_size = 20  # Set volume size in GB
    delete_on_termination = true  # Delete volume when instance is terminated
  }

  tags = {
    Name = "jenkins-instance"
  }
}
