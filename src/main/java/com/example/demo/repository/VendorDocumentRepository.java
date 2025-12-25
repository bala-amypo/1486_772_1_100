public interface VendorDocumentRepository extends JpaRepository<VendorDocument, Long> {

    List<VendorDocument> findByVendor_Id(Long vendorId);

    List<VendorDocument> findByVendor(Vendor vendor);

    @Query("SELECT vd FROM VendorDocument vd WHERE vd.expiryDate < :cutoffDate")
    List<VendorDocument> findExpiredDocuments(LocalDate cutoffDate);
}
