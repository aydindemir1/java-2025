# Production Notes
- Büyük keyspace'te `KEYS *` yerine `SCAN` kullan.
- Cache/geçici verilere use-case'e uygun TTL ver.
- Docker örneğinde eğitim amaçlı `maxmemory=256mb` ve `allkeys-lru` kullanılıyor.
- Write sonrası cache invalidation/güncelleme yap.
- Cache stampede için lock/double-check yaklaşımını değerlendir.
- Hot key'leri ölç; gerekirse sharding/local cache düşün.
- Cache sadece hızlandırıcıysa Redis arızasında graceful fallback uygun olabilir; source-of-truth ise hata gizlenmemeli.
