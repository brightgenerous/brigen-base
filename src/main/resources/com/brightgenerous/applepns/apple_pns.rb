require 'openssl'
require 'socket'
require 'json'

def apple_pns_init(server='localhost', port=80)

  return true

end

def apple_pns(devices, body, cert, key, server='gateway.push.apple.com', port=2195)

  payload = {"aps" => {"alert" => body, "sound" => "default"}}.to_json
  p_size = payload.bytesize
  if 256 < p_size
    raise
  end

  socket = TCPSocket.new(server, port)

  context = OpenSSL::SSL::SSLContext.new('SSLv3')
  context.cert = OpenSSL::X509::Certificate.new(cert)
  context.key  = OpenSSL::PKey::RSA.new(key)

  ssl = OpenSSL::SSL::SSLSocket.new(socket, context)
  ssl.connect

  devices.each do |device|
    d_bytes = [device].pack('H*')
    d_size = d_bytes.bytesize
    if d_size != 32
      raise
    end
    write_s = [0, d_size / 256, d_size % 256, d_bytes, p_size / 256, p_size % 256, payload].pack("ccca*cca*")
    ssl.write(write_s)
  end

  ssl.close
  socket.close

  return true

end
